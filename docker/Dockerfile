FROM postgres:16

# init 디렉토리의 파일들을 복사
COPY ./db/init /docker-entrypoint-initdb.d/

# sh 파일에 실행 권한 부여
RUN chmod +x /docker-entrypoint-initdb.d/*.sh
