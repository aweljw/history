>## springBootBase : spring boot basic project

>## springSecurityInmemory : Spring Security using Memory

>## springSecurityDB : Spring Security using DB

>## s3FileWork : Upload, download, and delete files using AWS-S3

>## spring cloud (springCloudConfig, springCloudClient)
>* springCloudConfig : config 설정을 담당할 서버
>
>* springCloudClient : springCloudConfig서버의 config설정을 받아와 실행될 서버
>
>* 서버 재시작 없이 설정파일 변경 순서
>	1. 설정파일 수정
>	2. http:localhost:8080/refresh(client주소) post 호출 필요(body 빈값)
>	
>	   **`※ 적용될 소스에 @RefreshScope 어노테이션 필수`**