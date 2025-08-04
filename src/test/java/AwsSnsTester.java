import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;

public class AwsSnsTester {

    public static void main(String[] args) {
        // SNS 클라이언트 생성
        SnsClient snsClient = SnsClient.builder()
                .region(Region.AP_NORTHEAST_2) // 서울 리전
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();

        // 메시지 내용
        String messageJson = "{\n" +
                "  \"default\": \"기본 메시지\",\n" +
                "  \"GCM\": \"{ \\\"notification\\\": { \\\"title\\\": \\\"알림 제목\\\", \\\"body\\\": \\\"알림 내용입니다.\\\" } }\"\n" +
                "}";

        // 엔드포인트 또는 토픽 ARN
        String endpointArn = "arn:aws:sns:ap-northeast-2:077672914621:tamibang-topic";

        // Publish 요청
        PublishRequest request = PublishRequest.builder()
                .messageStructure("json")
                .message(messageJson)
                .targetArn(endpointArn) // 또는 topicArn()
                .build();

        // 발송
        PublishResponse response = snsClient.publish(request);
        System.out.println("✅ 푸시 발송 성공: " + response.messageId());
    }
}


