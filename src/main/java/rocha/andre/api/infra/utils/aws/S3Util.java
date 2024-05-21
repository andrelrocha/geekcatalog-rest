package rocha.andre.api.infra.utils.aws;

import java.io.InputStream;

public class S3Util {
    private DependencyFactory dependencyFactory;
    public void uploadFile(String fileName, InputStream inputStream) {
        try {
            var s3Client = dependencyFactory.s3Client();
        } catch (Exception e) {
            throw new RuntimeException("Erro no proceesso de upload de uma imagem para o s3");
        }
    }
}
