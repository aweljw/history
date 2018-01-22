<#import "/layout/layout.ftl" as layout>

<@layout.userLayout title="main" pageNavigator=["main"]>
	<div class="container">
	    <div class="starter-template">
	        <h1>Spring Boot + Spring SecurityDB</h1>
	        <h2>1. Visit <a href="/admin">Admin page (Spring Security protected, Need Admin Role)</a></h2>
	        <h2>2. Visit <a href="/user">User page (Spring Security protected, Need User Role)</a></h2>
	        <h2>3. Visit <a href="/about">Normal page</a></h2>
	        <h2>4. Visit <a href="/login">Login page</a></h2>
	    </div>
	</div>
</@layout.userLayout>