<!DOCTYPE html>
<html lang="ko">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>fileList</title>		
	</head>
	<body>
		<h1>ㆍfile upload download sample</h1><br>
		<table border="1">
		    <colgroup>
		    	<col width="100px" />
		    	<col width="100px" />
		    	<col width="400px" />
		    	<col width="200px" />
		    	<col width="200px" />
		    	<col width="80px" />
		    </colgroup>
			<thead>
				<tr>
					<td>seq</td>
					<td>notiSeq</td>
					<td>uuid</td>
					<td>fileName</td>
					<td>생성일</td>
					<td>삭제</td>
				</tr>
			</thead>
			<#list fileList as list>
			<tbody>
				<tr>
					<td>${list.fileSeq}</td>
					<td>${list.notiSeq}</td>
					<td>${list.fileUuid}</td>
					<td><a href="/file/download?fileUuid=${list.fileUuid}">${list.fileName}</a></td>
					<td>${list.fileRegDate}</td>
					<td><a href="/file/deleteFile?fileUuid=${list.fileUuid}">삭제</a></td>
				</tr>
			</tbody>
			</#list>
		</table><br><br>
	
		<form method="post" action="/file/upload" enctype="multipart/form-data">
			<input type="file" name="filedata"><br>
			<input type="file" name="filedata"><br><br><br>
			<input type="submit" value="submit"><br>
		</form>		
	</body>
</html>
