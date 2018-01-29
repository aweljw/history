AWS SES MAIL발송 사용 예제
==============================================================================

### AWS SES 환경구성

1. AWS 회원가입, IAM 권한등록, 액세스 키 생성 (대부분의 aws 서비스의 공통사항으로 추후 문서화)
   - 자격 증명 등록
   		- 자격 증명에 대한 종류는 여러가지가 존재하나 해당 예제에서는 AWS에서 권장하는 방법으로 수행한다.
   		- 아래의 해당되는 위치에 ACCESS_KEY, SECRET_KEY를 입력
   			Linux, macOS, or Unix : ~/.aws/credentials
			Windows : C:\Users\USERNAME \.aws\credentials
            
			※참조 : https://docs.aws.amazon.com/ko_kr/sdk-for-java/v1/developer-guide/setup-credentials.html

2. Simple Email Service로 이동
![](/img/awsSes/capture1.PNG)
  
3. 리전을 선택하고 SES 생성
	- SES의 리전은 미국 동부, 서부, 아일랜드 3가지만 존재한다.(2018.01.26 기준)

4. left menu bar에서 Email Addresses를 선택한다.
![](/img/awsSes/capture2.PNG)

5. Verify a New Email Address를 클릭 후, 이메일을 등록한다.
![](/img/awsSes/capture3.PNG)

6. 생성된 이메일의 Status를 확인 후, left menu bar에서 Sending Statistics를 클릭한다.
![](/img/awsSes/capture4.PNG)

7. 여기까지 완료 시, 제한된 이메일을 발송할 수가 있다.
	- 최대 송신률 : 초당 이메일 1개
	- 발신 할당량 : 24시간당 이메일 200개
	- 발신 도메인 : 등록된 도메인으로만 발신 가능

	※참조 : https://docs.aws.amazon.com/ko_kr/ses/latest/DeveloperGuide/limits.html
![](/img/awsSes/capture5.PNG)

8. Request a Sending Limit Increase버튼을 클릭하여 Limit 제한을 푸는등의 설정이 가능하다.
   - 해당 기능을 수행해야 발신에 도메인 제한이 풀리게 되며 금액이 발생할 수 있는 부분이라 옵션확인이 필수. (추후 업데이트)

### AWS SES EMAIL 샘플예제 정보

###### 실행방법 : 서버 시작 시, 1,번과 2번의 해당되는 메일 발송이 시작된다.
   - 시작전 소스의 이메일 및 원하는 내용으로 수정을 한다.

##### 1. AWS SDK for Java를 사용하여 이메일 전송 (첨부파일 불가)
   https://docs.aws.amazon.com/ko_kr/ses/latest/DeveloperGuide/send-using-sdk-java.html

# pom.xml
	- aws-java-sdk-ses dependency 추가
		<dependency>
		    <groupId>com.amazonaws</groupId>
		    <artifactId>aws-java-sdk-ses</artifactId>
		    <version>1.11.269</version>
		</dependency>

# SendMail.java
	- location : com.awsSesUsingSDK.sendMail

	- 변수정보
		FROM      : 보내는 사람
		TO        : 받는사람
		SUBJECT   : 제목
		HTMLBODY  : 내용에 HTML사용
		TEXTBODY  : 내용에 TEXT사용
		CONFIGSET : HEADER정보 (사용안할 시 주석처리)	
			https://docs.aws.amazon.com/ko_kr/ses/latest/DeveloperGuide/using-configuration-sets-in-email.html

#### 2. Amazon SES API를 사용하여 원시 이메일 전송 (첨부파일 가능)
   https://docs.aws.amazon.com/ko_kr/ses/latest/DeveloperGuide/send-email-raw.html

# pom.xml
	- aws-java-sdk-ses, javax.mail dependency 추가
		<dependency>
		    <groupId>com.amazonaws</groupId>
		    <artifactId>aws-java-sdk-ses</artifactId>
		    <version>1.11.269</version>
		</dependency>
		<dependency>
			<groupId>com.sun.mail</groupId>
			<artifactId>javax.mail</artifactId>
			<version>1.6.0</version>
		</dependency>

# SendMailRaw.java
	- location : com.awsSesUsingSDK.sendMail

※ 지원되지 않는 첨부파일
	https://docs.aws.amazon.com/ko_kr/ses/latest/DeveloperGuide/mime-types-appendix.html