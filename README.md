# Java IBM MQ

- Get an IBM MQ queue for development in a container
https://developer.ibm.com/learningpaths/ibm-mq-badge/create-configure-queue-manager/

- Mac Arm64
```sh
git clone https://github.com/ibm-messaging/mq-container.git
cd mq-container
make build-devserver
#image => ibm-mqadvanced-server-dev
```

```sh
docker exec -ti ibmmq bash
dspmqver
```

https://localhost:9443/ibmmq
admin/pass