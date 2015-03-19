var user;

function setupPage(){
    //setup ajax request for user name
    var xhr = new XMLHttpRequest();
    var url = "/api/user";
    xhr.open( "GET", url, true );
    xhr.onreadystatechange = function() {
        if ( xhr.readyState != 4) return;
        if ( xhr.status == 200){
            var data = JSON.parse(xhr.responseText);
            user = data.username;
            getInfo(user);
        } else {
            alert("Unknown ERROR when getting current user.");
        }
    }
    xhr.send( null );
};

//once we have the username, we can obtain the user details
//    var form = document.getElementById("accountForm");
function getInfo(username){
    var xhr = new XMLHttpRequest();
    var url = "/api/account/"+username;
    xhr.open( "GET", url, true );
    xhr.onreadystatechange = function() {
        if ( xhr.readyState != 4) return;
        if ( xhr.status == 200){
            var data = JSON.parse(xhr.responseText);
            displayInfo(data);
        } else {
            alert("Unknown ERROR when getting current user's data.");
        }
    }
    xhr.send( null );
};

//once we have user details, create the form html
//the form will call another js function to update account
function displayInfo(userData){
    var html = "<form id='accountForm' action='javascript:void 1'>" +
        "<p>Account Name: "+userData.username+"</p>" +
        "<p>Name: "+userData.name+"</p>" +
        "<label>Change Name: <input type='text' name='name'></label>" +
        "<p>Email: "+userData.email+"</p>" +
        "<label>Change Email: <input type='text' name='email'></label>" +
        "<p>Change Password:</p>" +
        "<label>Old Password: <input type='password' name='oldPassword'></label><br>" +
        "<label>New Password: <input type='password' name='newPassword'></label><br>" +
        "<label>Verify Password: <input type='password' name='verifyPassword'></label><br>" +
        "<button name='submit' id='formSubmit'>Submit</button>" +
        "</form>";
    //get relevant tags
    var place = document.getElementById("formPlaceholder");
    //set innerhtml
    place.innerHTML = html;
    //rig method
    var form = document.getElementById("accountForm");
    form.submit.addEventListener("click", function(evt){
        updateAccount();
    });
};

//ajax request to update account
//make the page look like it's updated
//once we have a response, obtain user details
function updateAccount(){
    //package info into a JSON object
    var form = document.getElementById("accountForm");
    var packagedData = {"username":user, "name":form.name.value, "email":form.email.value, "oldPassword":form.oldPassword.value, "newPassword":form.newPassword.value};
    //ajax post request w/. data
    var xhr = new XMLHttpRequest();
    var url = "/api/account/"+user;
    xhr.open( "PUT", url, true );
    xhr.onreadystatechange = function() {
        if ( xhr.readyState != 4) return;
        if ( xhr.status == 200){
            var data = JSON.parse(xhr.responseText);
            getInfo(user);
        } else {
            alert("Unknown ERROR when setting current user's data.");
        }
    }
    xhr.send( JSON.stringify(packagedData) );

    //update now
//    displayInfo();
}