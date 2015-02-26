function deleteSelected() {
	var check = $('.deleteCheck');
	var dNames = $('#deletedUsers');
	var names = '';
	for(i = 0; i < check.length; i++){
		if(check[i].checked)
			names += check[i].id.split('_')[0] + " ";
	}
	dNames.attr('value', names);
	var f = $('#adminForm');
	f.attr('method', 'delete');
	f.attr('action', '/deletedUsers');
	f.submit()
}

var saveList = [];
		
function saveUsers(){
	var sl = $('#saveList');
	sl.attr('value', saveList.join(" "));
	var f = $('#adminForm');
	f.attr('action', '/savedUsers');
	f.submit()
}

function updateSave(name){
	var found = false;
	for(i = 0; i < saveList.length; i++){
		if(saveList[i] === name)
		{
			found = true;
			break;
		}
	}
	
	if(!found)
		saveList.push(name);

}

function insertUsers()
{
	var t = document.getElementById('scrollTable');
	var xmlHttp = new XMLHttpRequest();
    xmlHttp.open( "GET", "/allUsers", true );
	xmlHttp.onreadystatechange = function() {
		if ( xmlHttp.readyState != 4) return;
		if ( xmlHttp.status == 200 || xmlHttp.status == 400) {
			t.innerHTML = xmlHttp.responseText
		}
		else {
			t.innerHTML = "Unknown ERROR";
		}
	}
    xmlHttp.send( null );
}
