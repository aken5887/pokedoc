#!/bin/bash

REPOSITORY=/home/ec2-user/pokedoc/step2
PROJECT_NAME=pokedoc

echo ">>>>>>> Build 파일 복사"

cp $REPOSITORY/zip/*.jar $REPOSITORY/

echo ">>>>>>> 현재 구동중인 애플리케이션 pid 확인"

CURRENT_PID=$(pgrep -f ${PROJECT_NAME}.*.jar)

echo ">>>>>> 현재 구동 중인 애플리케이션 pid : $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
        echo ">>>>>>>> 현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다."
else
        echo ">>>>>>>> kill -15 $CURRENT_PID"
        kill -15 $CURRENT_PID
        sleep 5
fi

echo ">>>>>>> 새 애플리케이션 배포"

JAR_NAME=$(ls -tr $REPOSITORY/ | grep SNAPSHOT.jar | tail -n 1)

echo ">>>>>>> JAR_NAME : $REPOSITORY/$JAR_NAME"
echo ">>>>>>> $JAR_NAME 에 실행권한 추가"

chmod +x $JAR_NAME

echo ">>>>>>> $JAR_NAME 실행"

nohup java -jar $REPOSITORY/$JAR_NAME --spring.config.location=classpath:/application-real.properties,/home/ec2-user/pokedoc/application-real-db.properties,/home/ec2-user/pokedoc/application-oauth.properties --spring.profiles.active=real

sleep 5

tail -f nohup.out