# spring-kafka-example
> Spring boot - Kafka producer, consumer

> messages send and receive test example

##
### Prerequisites
- JDK 1.8
- Maven 3
- Kafka
  - https://kafka.apache.org/quickstart

##
### Environment
- Java 1.8
- Spring Boot 1.5.9
- Spring Kafka 1.3.2
- Spring Integration Kafka 2.3.0 (spring boot 1.5 이상 버전은 pom.xml에 명시적으로 version 2.3.0을 사용해야함)
- Maven 3.3.0
- MAC Sierra 10.12.6

##
### Project modules info
1. Producer Application
  - Book messages send -> Kafka broker

2. Consumer Application
  - Receives books

##
### Build
```
git clone https://github.com/jeonguk/spring-kafka-example.git
cd spring-kafka-example
```

```
mvn clean package
```

##
### TEST using Terminal

---

### Kafka RUN
#### run zookeeper server
```
Terminal 1: Start ZooKeeper. In your Kafka installation folder
- bin/zookeeper-server-start.sh config/zookeeper.properties
```
#### run kafka server
```
Terminal 2: Start KafkaServer. Go to your Kafka installation folder
- bin/kafka-server-start.sh config/server.properties
```

---


### Application run kakfa-consumer

#### consumer 1 kakfa-consumer
```
Terminal 3: Start first consumer with group id “group-one” and subscribed to FANTASY and HORROR genres
- mvn spring-boot:run -Dspring.kafka.consumer.group-id="group-one" -Drun.arguments="FANTASY,HORROR"
```

#### consumer 2 kakfa-consumer
```
Terminal 4: Start second consumer with group id “group-two” and subscribed to HORROR, ROMANCE and THRILLER genres
- mvn spring-boot:run -Dspring.kafka.consumer.group-id="group-two" -Drun.arguments="HORROR,ROMANCE,THRILLER"
```

#### producer run kakfa-producer
```
Terminal 5: Run producer. In the library folder, run the following command:
- mvn spring-boot:run
```

---


