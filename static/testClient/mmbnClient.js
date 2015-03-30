/*********************************************************************
** mmbnClient.js
** @author Jake Parsons MUN#201030616
** >Mega Man and Mega Man Batte Network Copyright Capcom.
** >Associated property used in accordance with Fair Use for educational purposes.
**
** -implements the client-side behaviour of the MMBN Game
** -includes all the references to resources we load from the server
*********************************************************************/

function GameClient(canvasId) {
	//game logic members
	this.gameMap = new GameMap();
	this.gameBackground;
	this.player = new Player();
	this.remotePlayer = new RemotePlayer();
	
	//server-side resource members
	this.gameIsLoaded = false; //true when all resources have been loaded
	
	this.gameModel; //the ajax info preloaded from the server
	
	this.mapImg; //the sprites of the tile frames
	this.tileImg; //the sprite of the tile surfaces
	this.bgImg; //the sprite for the background
	this.playersImg; //the sprite of all the players
	this.enemyImg; //sprites for blue side players
	this.eventsImg; //the sprite of all the events
	this.chipMenu;
	this.chipIcons;
	this.imgLoaded = [false, false, false, false, false]; //TODO please make the image resource class work this is not clean
	
	this.imgResources = [];
	this.allImgResourcesReady = function() {
		if(this.imgResources.length == 0) { return false; }
		for(var i = 0; i < this.imgResources.length; i=i+1) {
			if(!this.imgResources[i].imgReady) { return false; }
		}
		return true;
	}
	
	//the background image is loaded somewhat differently TODO hack this out and make it a little less ugly please!
	var bgImgTemp = new Image();
	var delegate = this;
	bgImgTemp.onload = function() {
		console.log("Loaded an image from ", bgImgTemp.src);
		delegate.imgLoaded[2] = true;
		delegate.bgImg = bgImgTemp;
		delegate.gameBackground = new GameBackground(bgImgTemp);
	}
	bgImgTemp.src = "bg1.png";
	//TODO hack this out and make it a little less ugly please!
	
	//timing members
	this.renderCaller;
	this.requestServerUpdateCaller;
	this.frameRate = 30.;
	this.updateDelay = 20;
	
	//html document members
	//html document members---canvas members
	this.canvas = document.getElementById(canvasId);
	this.cntxt = this.canvas.getContext("2d");
	this.windowWidth;
	this.windowHeight;
	this.cnvsScaleFactor = -1; //we have to resize initially (see resize function)
	this.mapOffsetX;
	this.mapOffsetY;
	
	//event handlers
	this.regdKeys = new GameKeys();
	this.regdKeyHandlersUp = new GameKeyHandler(this.regdKeys);
	this.regdKeyHandlersUp();
	
	//receive data from the server
	this.latestUpdate = new ServerState();
	this.updateRcvr = serverStateUpdater(this.latestUpdate, this.updateDelay);
	this.updateRcvr();
	
	//send data to the server
	this.updateSender = new serverUpdaterSendHandler(this.regdKeys, this.updateDelay);
	this.updateSender();
	
	//constructor work
	this.setCanvasScaleFactor();
	window.onresize = function(event) { delegate.setCanvasScaleFactor() }
	//try to set up the server
	this.modelSetup_send();
	//try to call the server twice as frequently as we render the client
	//delegate = this;
	//setTimeout(function() { delegate.renderClient() }, 3500);
	this.renderCaller = window.setInterval( function() { delegate.renderClient() }, (1000. / this.frameRate) );
	this.requestServerUpdateCaller = window.setInterval( function() { delegate.modelUpdate_send() }, 2*(1000. / this.frameRate) );
}
//---GameClient Class Methods---
//---Game Logic Methods
/*********************************************************************
** XXXXX
** -XXXXX
*********************************************************************/

//---HTTP/XMLHTTP Server Request Methods---
/*********************************************************************
** loadSpriteMap
** -loads a new sprite map from the server according to AJAX recvd.
** -sets a callback to indicate whether it was loaded successfully.
*********************************************************************/
GameClient.prototype.loadSpriteMap = function(spriteMapUrl, onLoadFlagIndx, passer) {
	image = new Image();
	image.onload = function() {
		console.log("Loaded an image from ", image.src);
		passer.imgLoaded[onLoadFlagIndx] = true;
		//if all the resources have been loaded the game is loaded
		if(passer.imgLoaded[0] && passer.imgLoaded[1] && passer.imgLoaded[2]) {
			console.log("Game is loaded!");
		}
	}
	image.src = spriteMapUrl;
	return image;
};

/*********************************************************************
** modelSetup
** -request the model of the game we will use to play this round
** -includes all the references to resources we load from the server
** -called only once, when beginning the game
*********************************************************************/
GameClient.prototype.modelSetup_send = function() {
	var xmlHttp_modelSetup = new XMLHttpRequest();
	//this part is old-fashioned - change it so you pull this from the server when you're done testing
	var url = "sprites.txt";
	var delegate = this;
	xmlHttp_modelSetup.onreadystatechange = function() {
		if(xmlHttp_modelSetup.readyState == 4 && xmlHttp_modelSetup.status == 200) {
			if(xmlHttp_modelSetup.repsonseText !== null) {
				delegate.gameModel = JSON.parse(xmlHttp_modelSetup.responseText);
				delegate.gameModelLoaded = true;
				
				//load the tile frame sprites
				delegate.mapImg = delegate.loadSpriteMap(delegate.gameModel.tileFrames.spriteSrc, 0, delegate);
				
				//load the tile surface sprites
				delegate.tileImg = delegate.loadSpriteMap(delegate.gameModel.tileSurfaces.spriteSrc, 1, delegate);
				
				//TODO maybe load the BG images from that too?
				//load the player sprites
				delegate.playersImg = delegate.loadSpriteMap(delegate.gameModel.megaman.spriteSrc, 3, delegate);
				delegate.enemyImg = delegate.loadSpriteMap(delegate.gameModel.enemy.spriteSrc, 4, delegate);
				delegate.chipMenu = delegate.loadSpriteMap(delegate.gameModel.menu.spriteSrc, 5, delegate);
				delegate.chipIcons = delegate.loadSpriteMap(delegate.gameModel.chipIcons.spriteSrc, 6, delegate);
				
				//load the events sprites
				//this.eventsImg = loadSpriteMap(this.gameModel.XXXXX.spriteSrc, this.eventsImgLoaded);
			}
		}
		//else { console.error("Failed to receive correct setup data from the server!"); }
	}
	xmlHttp_modelSetup.open("GET", url, true);
	xmlHttp_modelSetup.send();
};

/*********************************************************************
** modelUpdate
** -request the model of the game at the next server tick
** -may include a request to update the model if the user acts
** -called on a timer, but does not run until the game is loaded
*********************************************************************/
GameClient.prototype.modelUpdate_send = function() {
	if(this.gameIsLoaded) {
		var xmlHttp_modelUpdate = new XMLHttpRequest();
		
	}
};

//---HTML Document Related Methods---
/*********************************************************************
** setCanvasScaleFactor
** -reconfig the canvas when setting up or changing window size
*********************************************************************/
GameClient.prototype.setCanvasScaleFactor = function() {
	//get the current size of the browser window
	this.windowWidth = window.innerWidth;
	this.windowHeight = window.innerHeight;
	
	//track the last scaling factor to know if we have to redraw the canvas
	var lastScalingFactor = this.cnvsScaleFactor;
	//use smallest relative dimension to determine scaling factor
	if( (this.windowWidth / 3.) >= (this.windowHeight / 2.) ) {
		this.cnvsScaleFactor = Math.floor(this.windowHeight / 200.);
	} else { 
		this.cnvsScaleFactor = Math.floor(this.windowWidth / 300.);
	}
	//but, make sure we don't scale below the base sprite sizes
	if(this.cnvsScaleFactor < 1) { this.cnvsScaleFactor = 1; }
	
	if(this.cnvsScaleFactor != lastScalingFactor) { //we do this if the old value and new value are different, e.g. we have to rescale the canvas
		// resize the canvas
		this.canvas.width = (this.cnvsScaleFactor * 300);
		this.canvas.height = (this.cnvsScaleFactor * 200);
		
		//use the new canvas size to set where the center is (and hence where we draw the map)
		this.mapOffsetX = (this.canvas.width - (this.gameMap.mapWidth * 40 * this.cnvsScaleFactor) ) / 2;
		this.mapOffsetY = 5 * ((this.canvas.height - (((24*(this.gameMap.mapHeight-1) ) + 33)*this.cnvsScaleFactor) ) / 6);
	}
};

/*********************************************************************
** renderClient (related utility methods below)
** -render the client state with the latest model state info.
** -called by a setTimeout() which should timeout at this.frameRate.
**
** as a note, drawing sprites (sub-image of loaded image) looks like:
** this.cntxt.drawImage(IMGSRC, SPRITEXPOS, SPRITEYPOS, 
**		SPRITEWIDTH, SPRITEHEIGHT, CANVASXPOS, CANVASYPOS, 
**		CANVASWIDTH, CANVASHEIGHT);
*********************************************************************/
GameClient.prototype.renderClient = function() {
	if(this.imgLoaded[0] && this.imgLoaded[1] && this.imgLoaded[2] && this.imgLoaded[3] && this.imgLoaded[4] && this.imgLoaded[5] && this.imgLoaded[6]) {
		this.clearCanvas();
		this.renderAndUpdateBackground();
		//check that the order of these two is correct
		this.renderGameMap();
		this.renderHUD();
		this.renderPlayersAndEvents();
		this.renderMenu();
	}
	else { console.log("not loaded: " + this.imgLoaded[0]  + ", " + this.imgLoaded[1] + ", " + this.imgLoaded[2]); }
};

GameClient.prototype.clearCanvas = function() {
	this.cntxt.clearRect(0, 0, this.canvas.width, this.canvas.height);
};

GameClient.prototype.renderAndUpdateBackground = function() {
	//set up the background
	var bgPtrn = this.cntxt.createPattern(this.gameBackground.bgImage, 'repeat');
	this.cntxt.fillStyle = bgPtrn;
	this.cntxt.translate(-this.gameBackground.frameIter, this.gameBackground.frameIter);
	this.cntxt.fillRect(this.gameBackground.frameIter, -this.gameBackground.frameIter, this.canvas.width + this.gameBackground.frameIter, this.canvas.height + this.gameBackground.frameIter);
	this.cntxt.translate(this.gameBackground.frameIter, -this.gameBackground.frameIter);
	//update the background's state
	this.gameBackground.iterateBg();
};

GameClient.prototype.renderHUD = function() {
	if(this.latestUpdate.serverStateJson != null){
		var plyrData = JSON.parse(this.latestUpdate.serverStateJson.myState);
		var enemyData = JSON.parse(this.latestUpdate.serverStateJson.enemyState);
		this.cntxt.fillStyle = "red";
		this.cntxt.font="20px mmbnFont";
		this.cntxt.fillText(plyrData.health, 0, 30);
		this.cntxt.fillText(enemyData.health, this.canvas.width - 30, 30);
	}
};

GameClient.prototype.renderMenu = function(){
    if (this.latestUpdate.serverStateJson.state == "CHIPMENU"){
        var menu = this.gameModel.menu.frames[0];

        //draw the base menu
        this.cntxt.drawImage(this.chipMenu,
            menu.xPos,
            menu.yPos,
            menu.width,
            menu.height,
            menu.cursorX*this.cnvsScaleFactor,
            menu.cursorY*this.cnvsScaleFactor,
            menu.width*this.cnvsScaleFactor,
            menu.height*this.cnvsScaleFactor);

         //draw the available chips

         //draw the selected chips

         var cursorPos=4;
         var scale=this.cnvsScaleFactor;
         //draw the cursor
         this.cntxt.beginPath();
         this.cntxt.lineWidth="3";
         this.cntxt.strokeStyle="red";
         this.cntxt.rect((9+(cursorPos*16))*scale,130*scale,14*scale,14*scale);
         this.cntxt.stroke();
    }
}

GameClient.prototype.renderGameMap = function() {
	//draw each map tile, going in vertical slices (players tend to own tiles only in slices)
	for(var i = 0; i < this.gameMap.mapWidth; i=i+1) {
		for(var j=0; j < this.gameMap.mapHeight; j=j+1) {
			var xPos = this.mapOffsetX + (i * 40 * this.cnvsScaleFactor);
			var yPos = this.mapOffsetY + (j * 24 * this.cnvsScaleFactor);
			
			var tileSpriteXPos, tileSpriteYPos, tileWidth, tileHeight;
			var sfcTileState, sfcSpriteXPos, sfcSpriteYPos, sfcWidth, sfcHeight, sfcCnvsXPos, sfcCnvsYPos;
			//draw the frame of the tile, the image for which is selected according to ownership
			if(this.gameMap.mapTiles[i][j].playerOwnsTile) { //player-owned tiles are red
				//get the information about the sprite
				tileSpriteXPos = this.gameModel.tileFrames.frames[j].xPos;
				tileSpriteYPos = this.gameModel.tileFrames.frames[j].yPos;
				tileWidth = this.gameModel.tileFrames.frames[j].width;
				tileHeight = this.gameModel.tileFrames.frames[j].height;
				//draw the sprite
				this.cntxt.drawImage(this.mapImg, tileSpriteXPos, tileSpriteYPos, tileWidth, tileHeight, xPos, yPos, tileWidth*this.cnvsScaleFactor, tileHeight*this.cnvsScaleFactor);
			}
			else { //opponent-owned tiles are blue
				//get the information about the sprite
				tileSpriteXPos = this.gameModel.tileFrames.frames[j+3].xPos;
				tileSpriteYPos = this.gameModel.tileFrames.frames[j+3].yPos;
				tileWidth = this.gameModel.tileFrames.frames[j+3].width;
				tileHeight = this.gameModel.tileFrames.frames[j+3].height;
				//draw the sprite
				this.cntxt.drawImage(this.mapImg, tileSpriteXPos, tileSpriteYPos, tileWidth, tileHeight, xPos, yPos, tileWidth*this.cnvsScaleFactor, tileHeight*this.cnvsScaleFactor);
			}
			//draw the surface of the tile, the image for which is selected according to tile state
			sfcTileState = this.gameMap.mapTiles[i][j].tileState;
			sfcSpriteXPos = this.gameModel.tileSurfaces.frames[sfcTileState + j].xPos;
			sfcSpriteYPos = this.gameModel.tileSurfaces.frames[sfcTileState + j].yPos;
			sfcWidth = this.gameModel.tileSurfaces.frames[sfcTileState + j].width;
			sfcHeight = this.gameModel.tileSurfaces.frames[sfcTileState + j].height;
			sfcCnvsXPos = xPos + (this.gameModel.tileSurfaces.frames[sfcTileState + j].cursorX*this.cnvsScaleFactor);
			sfcCnvsYPos = yPos + (this.gameModel.tileSurfaces.frames[sfcTileState + j].cursorY*this.cnvsScaleFactor);
			this.cntxt.drawImage(this.tileImg, sfcSpriteXPos, sfcSpriteYPos, sfcWidth, sfcHeight, sfcCnvsXPos, sfcCnvsYPos, sfcWidth*this.cnvsScaleFactor, sfcHeight*this.cnvsScaleFactor);
		}
	}
};

GameClient.prototype.renderPlayersAndEvents = function() {

	//process upcoming events to determine which will be executed
	var frameEvents = this.latestUpdate.serverStateJson;
	if(frameEvents === null) { return; }
//	this.latestUpdate.serverStateJson = null;
	//draw any events which require animating
	
	//draw the players
	//local player
    var plyrData = JSON.parse(frameEvents.myState);
    var xLoc = this.mapOffsetX + (40 * this.cnvsScaleFactor * plyrData.x);
    var yLoc = this.mapOffsetY + (24 * this.cnvsScaleFactor * plyrData.y);
    var img;
    var side;
    if (plyrData.side == "RED"){
        img = this.playersImg;
        side = "megaman";
    } else if (plyrData.side == "BLUE"){
        img = this.enemyImg;
        side = "enemy";
    }

    if (plyrData.action == "NONE") {this.player.frameIter = 0;}
    else if (plyrData.action == "BUSTER") {this.player.frameIter = 7 + plyrData.actionIndex;}

	this.cntxt.drawImage(img,
		this.gameModel[side].frames[this.player.frameIter].xPos,
		this.gameModel[side].frames[this.player.frameIter].yPos,
		this.gameModel[side].frames[this.player.frameIter].width,
		this.gameModel[side].frames[this.player.frameIter].height,
		xLoc + (this.gameModel[side].frames[this.player.frameIter].cursorX*this.cnvsScaleFactor),
		yLoc + (this.gameModel[side].frames[this.player.frameIter].cursorY*this.cnvsScaleFactor),
		this.gameModel[side].frames[this.player.frameIter].width*this.cnvsScaleFactor,
		this.gameModel[side].frames[this.player.frameIter].height*this.cnvsScaleFactor);

	//remote player
    var enemyData = JSON.parse(frameEvents.enemyState);
    var exLoc = this.mapOffsetX + (40 * this.cnvsScaleFactor * enemyData.x);
    var eyLoc = this.mapOffsetY + (24 * this.cnvsScaleFactor * enemyData.y);
    else if (plyrData.action == "SWORD") {this.player.frameIter = 17 + plyrData.actionIndex;}

	this.cntxt.drawImage(img,
		this.gameModel[side].frames[this.player.frameIter].xPos,
		this.gameModel[side].frames[this.player.frameIter].yPos,
		this.gameModel[side].frames[this.player.frameIter].width,
		this.gameModel[side].frames[this.player.frameIter].height,
		xLoc + (this.gameModel[side].frames[this.player.frameIter].cursorX*this.cnvsScaleFactor),
		yLoc + (this.gameModel[side].frames[this.player.frameIter].cursorY*this.cnvsScaleFactor),
		this.gameModel[side].frames[this.player.frameIter].width*this.cnvsScaleFactor,
		this.gameModel[side].frames[this.player.frameIter].height*this.cnvsScaleFactor);

	//remote player
    var enemyData = JSON.parse(frameEvents.enemyState);
    var exLoc = this.mapOffsetX + (40 * this.cnvsScaleFactor * enemyData.x);
    var eyLoc = this.mapOffsetY + (24 * this.cnvsScaleFactor * enemyData.y);
    else if (plyrData.action == "CANNON") {this.player.frameIter = 29+ plyrData.actionIndex;}

	this.cntxt.drawImage(img,
		this.gameModel[side].frames[this.player.frameIter].xPos,
		this.gameModel[side].frames[this.player.frameIter].yPos,
		this.gameModel[side].frames[this.player.frameIter].width,
		this.gameModel[side].frames[this.player.frameIter].height,
		xLoc + (this.gameModel[side].frames[this.player.frameIter].cursorX*this.cnvsScaleFactor),
		yLoc + (this.gameModel[side].frames[this.player.frameIter].cursorY*this.cnvsScaleFactor),
		this.gameModel[side].frames[this.player.frameIter].width*this.cnvsScaleFactor,
		this.gameModel[side].frames[this.player.frameIter].height*this.cnvsScaleFactor);

	//remote player
    var enemyData = JSON.parse(frameEvents.enemyState);
    var exLoc = this.mapOffsetX + (40 * this.cnvsScaleFactor * enemyData.x);
    var eyLoc = this.mapOffsetY + (24 * this.cnvsScaleFactor * enemyData.y);
    if (enemyData.side == "RED"){
        img = this.playersImg;
        side = "megaman";
    } else if (enemyData.side == "BLUE"){
        img = this.enemyImg;
        side = "enemy";
    }
    if (enemyData.action == "NONE") {this.player.frameIter = 0;}
    else if (enemyData.action == "BUSTER") {this.player.frameIter = 7 + enemyData.actionIndex;}

    this.cntxt.drawImage(img,
        this.gameModel[side].frames[this.player.frameIter].xPos,
        this.gameModel[side].frames[this.player.frameIter].yPos,
        this.gameModel[side].frames[this.player.frameIter].width,
        this.gameModel[side].frames[this.player.frameIter].height,
        exLoc + (this.gameModel[side].frames[this.player.frameIter].cursorX*this.cnvsScaleFactor),
        eyLoc + (this.gameModel[side].frames[this.player.frameIter].cursorY*this.cnvsScaleFactor),
        this.gameModel[side].frames[this.player.frameIter].width*this.cnvsScaleFactor,
        this.gameModel[side].frames[this.player.frameIter].height*this.cnvsScaleFactor);
};
    else if (enemyData.action == "SWORD") {this.player.frameIter = 17 + enemyData.actionIndex;}//16,17,18

    this.cntxt.drawImage(img,
        this.gameModel[side].frames[this.player.frameIter].xPos,
        this.gameModel[side].frames[this.player.frameIter].yPos,
        this.gameModel[side].frames[this.player.frameIter].width,
        this.gameModel[side].frames[this.player.frameIter].height,
        exLoc + (this.gameModel[side].frames[this.player.frameIter].cursorX*this.cnvsScaleFactor),
        eyLoc + (this.gameModel[side].frames[this.player.frameIter].cursorY*this.cnvsScaleFactor),
        this.gameModel[side].frames[this.player.frameIter].width*this.cnvsScaleFactor,
        this.gameModel[side].frames[this.player.frameIter].height*this.cnvsScaleFactor);
};
    else if (enemyData.action == "CANNON") {this.player.frameIter = 29 + enemyData.actionIndex;}//28,29,20

    this.cntxt.drawImage(img,
        this.gameModel[side].frames[this.player.frameIter].xPos,
        this.gameModel[side].frames[this.player.frameIter].yPos,
        this.gameModel[side].frames[this.player.frameIter].width,
        this.gameModel[side].frames[this.player.frameIter].height,
        exLoc + (this.gameModel[side].frames[this.player.frameIter].cursorX*this.cnvsScaleFactor),
        eyLoc + (this.gameModel[side].frames[this.player.frameIter].cursorY*this.cnvsScaleFactor),
        this.gameModel[side].frames[this.player.frameIter].width*this.cnvsScaleFactor,
        this.gameModel[side].frames[this.player.frameIter].height*this.cnvsScaleFactor);
};



function GameMap() {
	//data model
	this.mapWidth = 6.;
	this.mapHeight = 3.;
	this.mapTiles = [];
	for(var i=0; i < this.mapWidth; i=i+1) {
		if( i < this.mapWidth / 2.) { this.mapTiles.push( [new GameMapTile(true, 0), new GameMapTile(true, 0),new GameMapTile(true, 0)] ); }
		else { this.mapTiles.push( [new GameMapTile(false, 0), new GameMapTile(false, 0),new GameMapTile(false, 0)] ) }
	}
	
	//sprites
	this.mapFrameSpriteMap;
	this.mapTilesSpriteMap;
}



function GameMapTile(isPlayerOwned, stateNum) {
	this.playerOwnsTile = isPlayerOwned;
	this.tileState = stateNum;
	this.isTileOccupied;
	this.tileOccupant;
}



function Player() {
	this.frameIter = 0;
}



function RemotePlayer() {
	
}



function SpriteMap() {
	
}



function GameHUD() {
	
}



function GameBackground(imgIn) {
	this.isReady; //set when the image is loaded and set
	this.bgImage = imgIn;
	this.bgPattern;
	this.frameIter = 0;
	this.maxFrameIter = 100; //assume all bg tiles are 100x100 for now.
}
//---GameBackground Class Methods---
GameBackground.prototype.iterateBg = function() {
	this.frameIter = (this.frameIter + 1) % this.maxFrameIter;
};

GameBackground.prototype.setUpBg = function(loadedImg) {
	this.bgImage = loadedImg;
	this.isReady = true;
};

function ImageResource(imgSrcIn) {
	this.imgReady = false;
	this.img = new Image();
	this.img.onload = function() {
		console.log("Loaded an image from ", this.img.src);
		this.imgReady = true;
	}
	this.img.src = imgSrcIn;
}

function GameKeys() {
	this.moveUp = 87;
	this.moveDown = 83;
	this.moveLeft = 65;
	this.moveRight = 68;
	this.buster = 190;
	this.chip = 188;
	this.endPhase = 32;
	
	this.lastKeyDown = 0;
	this.lastKeyUp = 0;
	this.lastEvent = null;
}

function GameKeyHandler(gkIn) {
	return function() {
		document.addEventListener('keyUp', function(e) {
			switch(e.keyCode){
				case gkIn.moveUp: //moveup down shouldn't do anything
					//console.log("moveUp down");
					//gkIn.lastKeyDown = gkIn.moveUp;
					//gkIn.lastEvent = JSON.stringify({event:"movement",value:"up"});
					break;
				case gkIn.moveDown: //moveleft down shouldn't do anything
					//console.log("moveDown down");
					//gkIn.lastKeyDown = gkIn.moveDown;
					//gkIn.lastEvent = JSON.stringify({event:"movement",value:"down"});
					break;
				case gkIn.moveLeft: //moveright down shouldn't do anything
					//console.log("moveLeft down");
					//gkIn.lastKeyDown = gkIn.moveLeft;
					//gkIn.lastEvent = JSON.stringify({event:"movement",value:"left"});
					break;
				case gkIn.moveRight: //movedown down shouldn't do anything
					//console.log("moveRight down");
					//gkIn.lastKeyDown = gkIn.moveRight;
					//gkIn.lastEvent = JSON.stringify({event:"movement",value:"right"});
					break;
				case gkIn.buster: //buster charge
					console.log("buster up");
					gkIn.lastKeyDown = gkIn.buster;
					gkIn.lastEvent = JSON.stringify({event:"buster",value:"up"});
					break;
				case gkIn.chip: //chip down doesn't do anything
					//lastEvent = JSON.stringify({event:"chip",value:""});
					//console.log("chip down");
					break;
				case gkIn.endPhase: //endphase down shouldn't do anything
//				    gkIn.lastKeyDown = gkIn.endPhase;
//					gkIn.lastEvent = JSON.stringify({event:"menu",value:""});
//					console.log("endPhase up");
					break;
			}
		}, false);
		document.addEventListener('keydown', function(e) {
			switch(e.keyCode){
				case gkIn.moveUp: //moveup
					console.log("moveUp up");
					gkIn.lastKeyDown = gkIn.moveUp;
					gkIn.lastEvent = JSON.stringify({event:"movement",value:"up"});
					break;
				case gkIn.moveDown: //moveleft
					console.log("moveDown up");
					gkIn.lastKeyDown = gkIn.moveDown;
					gkIn.lastEvent = JSON.stringify({event:"movement",value:"down"});
					break;
				case gkIn.moveLeft: //moveright
					console.log("moveLeft up");
					gkIn.lastKeyDown = gkIn.moveLeft;
					gkIn.lastEvent = JSON.stringify({event:"movement",value:"left"});
					break;
				case gkIn.moveRight: //movedown
					console.log("moveRight up");
					gkIn.lastKeyDown = gkIn.moveRight;
					gkIn.lastEvent = JSON.stringify({event:"movement",value:"right"});
					break;
				case gkIn.buster: //buster shoot
					console.log("buster up");
					gkIn.lastKeyDown = gkIn.buster;
					gkIn.lastEvent = JSON.stringify({event:"buster",value:"up"});
					break;
				case gkIn.chip: //chip
					console.log("chip up");
					gkIn.lastKeyDown = gkIn.chip;
					gkIn.lastEvent = JSON.stringify({event:"chip",value:""});
					break;
				case gkIn.endPhase: //endphase
					console.log("endPhase up");
					gkIn.lastKeyDown = gkIn.endPhase;
					gkIn.lastEvent = JSON.stringify({event:"menu",value:""});
					break;
			}
		}, false);
	};
}

function serverUpdaterSendHandler(gkIn, sendDelay) {
	return function() {
		window.setInterval(function() {
			if (gkIn.lastEvent != null){
				var xmlHttp = new XMLHttpRequest();
				xmlHttp.open( "POST", "/game/sendAction", true );
				xmlHttp.onreadystatechange = function() {
					if ( xmlHttp.readyState != 4) return;
					if ( xmlHttp.status == 200 || xmlHttp.status == 400)
					{
						//var response = xmlHttp.responseText;
					}
					else { alert("Unknown ERROR when saving."); }
				}
				xmlHttp.send(gkIn.lastEvent);
				gkIn.lastEvent = null;
			}
		}, sendDelay);
	}
}

function ServerState() {
	this.serverStateJson = null;
}

function serverStateUpdater(serverStateIn, sendDelay) {
	return function() {
		window.setInterval(function() {
//			if(serverStateIn.serverStateJson == null) {
				var xmlHttp = new XMLHttpRequest();
				xmlHttp.open( "POST", "/game/gameUpdate", true );
				xmlHttp.onreadystatechange = function() {
					if ( xmlHttp.readyState != 4) return;
					if ( xmlHttp.status == 200 || xmlHttp.status == 400)
					{
						serverStateIn.serverStateJson = JSON.parse(xmlHttp.responseText);
					}
					else { alert("Unknown ERROR when saving."); }
				}
				xmlHttp.send("");
				//TODO null serverStateIn.serverStateJson when you render
//			} //else we haven't animated this frame yet
		}, sendDelay);
	}
}