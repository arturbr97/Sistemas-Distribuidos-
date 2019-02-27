function setUID(uid){
	sessionStorage.setItem("uid", uid);
}

function getUID(){
	return sessionStorage.getItem("uid");
}

function setDataSession(data){
	sessionStorage.setItem("authenticatedUser", true);
	sessionStorage.setItem("userData", JSON.stringify(data));
}

function invalidateSession(){
	sessionStorage.clear();
}

function getDataSession(){
	return JSON.parse(sessionStorage.getItem("userData"));
}

function validSession(){
	var sessionValid = sessionStorage.getItem("authenticatedUser");
	if(sessionValid){
		window.location.href = "#!/manageJobs";
	}else{
		window.location.href = "#!/Login";
	}
}