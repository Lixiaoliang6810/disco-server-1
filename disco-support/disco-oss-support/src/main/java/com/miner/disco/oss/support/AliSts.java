package com.miner.disco.oss.support;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Created by lubycoder@163.com 2018/12/24
 */
@Slf4j
@Component
public class AliSts {

    @Value("${ali.oss.sts.endpoint}")
    private String endpoint;

    @Value("${ali.oss.backname}")
    private String backname;

    @Value("${ali.oss.sts.access-key-id}")
    private String accessKeyId;

    @Value("${ali.oss.sts.access-key-secret}")
    private String accessKeySecret;

    @Value("${ali.oss.sts.role-arn}")
    private String roleArn;

    private static final String roleSessionName = "public-resource-session";

    private static final String product = "Sts";

    private static final String endpointName = "";

    private static final String regionId = "";

    public OSSSTSResponse authorize() throws ClientException {
        String policy = "{\n" +
                "  \"Statement\": [\n" +
                "    {\n" +
                "      \"Action\": [\n" +
                "        \"oss:PutObject\"\n" +
                "      ],\n" +
                "      \"Effect\": \"Allow\",\n" +
                "      \"Resource\": [\"acs:oss:*:*:" + backname + "/*\"]\n" +
                "    }\n" +
                "  ],\n" +
                "  \"Version\": \"1\"\n" +
                "}";
        DefaultProfile.addEndpoint(endpointName, regionId, product, endpoint);
        IClientProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        final AssumeRoleRequest request = new AssumeRoleRequest();
        request.setMethod(MethodType.POST);
        request.setRoleArn(roleArn);
        request.setRoleSessionName(roleSessionName);
        request.setPolicy(policy);
        final AssumeRoleResponse response = client.getAcsResponse(request);
        OSSSTSResponse osssts = new OSSSTSResponse();
        osssts.setAccessKeyId(response.getCredentials().getAccessKeyId());
        osssts.setAccessKeySecret(response.getCredentials().getAccessKeySecret());
        osssts.setSecurityToken(response.getCredentials().getSecurityToken());
        osssts.setExpiration(response.getCredentials().getExpiration());
        return osssts;
    }


}
