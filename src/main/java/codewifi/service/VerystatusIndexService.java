package codewifi.service;

import codewifi.repository.model.VerystatusUserModel;
import codewifi.request.very.VerystatusIndexRequest;
import codewifi.response.very.VerystatusIndexResponse;
import org.springframework.stereotype.Service;

@Service
public interface VerystatusIndexService {
    VerystatusIndexResponse getUserIndex(VerystatusIndexRequest verystatusIndexRequest, VerystatusUserModel verystatusUserModel);
}
