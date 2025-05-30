window.onload = function(){
	document.getElementById("signupForm").addEventListener("submit", function(event){
		event.preventDefault();
		signUp();
	})
}

//アカウント登録確認および登録完了確認を行う
function signUp(){
	if (confirm("アカウントを登録しますか？")){
		const jsonSignUpData = 
		{
			"name": document.getElementById("name").value,
			"mail": document.getElementById("mail").value,
			"password": document.getElementById("password").value
		}
	    // TODO: レスポンスからエラーメッセージを取得してAlertで表示する
		fetch('sign-up', {
			method: "POST",
			headers: {"Content-Type": "appliction/json"},
			body: JSON.stringify(jsonSignUpData)
		})
		.then(response => {
			if (response.ok) {
		        alert("アカウント登録が完了しました。");
		        window.location.href = "login";
		    }else{
				alert("アカウント登録に失敗しました。");
				window.location.reload();
			}
		})
	}
}
	