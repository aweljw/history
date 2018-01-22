<#import "/spring.ftl" as spring />
<#macro userLayout title="layout" pageNavigator=[]>
	<@layoutSkeleton>
		<div>
			<#include "/layout/header.ftl"/><br/>
			////////////////////////////////////////<br/>
			<#nested/><br/>
			////////////////////////////////////////<br/>
			<#include "/layout/footer.ftl"/><br/>
		</div>		
	</@layoutSkeleton>
</#macro>

<#macro layoutSkeleton>
<@compress single_line=true>
<!DOCTYPE html>
<html lang="ko">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title></title>
	</head>
	<body>
		<#nested />
	</body>
</html>
</@compress>
</#macro>