# knu-notice-server

착한 선배 애플리케이션의 Django Framework 서버를 Spring Boot로 변경하기 위한 레포

[기존 서버 Repo](https://github.com/ppcomp/knu-notice-server) 

[Android Repo](https://github.com/ppcomp/knu-notice-client)



**서버 사용법**

apiserver에 yml 파일을 추가해준다.

![apiserver resources](https://user-images.githubusercontent.com/59406960/167166200-3721a7a9-7534-4e3e-83b7-090a4c4e7b05.png)



alarmserver에 yml 파일과 firebase key파일을 추가해준다.

![alarmserver resources](https://user-images.githubusercontent.com/59406960/167166281-8fbbe3d8-d407-4601-a656-f098ae338849.png)

apiserver에는 Query DSL을 적용하기 위한 Q 클래스가 build/generated/kapt 아래에 생성되어 있어야 한다.

만약 생성이 안되어 있다면 kaptKotlin을 rebuild해본다.

![Query DSL Q class](https://user-images.githubusercontent.com/59406960/167166573-995b61ed-8b04-49cb-9503-0f49d299e93c.png)