<a href="/">home</a> (header)
<div>
	<#if !authentication??>
		<a href="/login">로그인</a>
	</#if>
	<#if authentication?? && authentication.userId??>
		<b>id : ${authentication.userId!""}</b><br/>
		<b>password : ${authentication.password!""}</b><br/>
	</#if>
	
	
	<form action="/logout" method="post">
	    <input type="submit" value="로그아웃"/>
	</form>
</div>