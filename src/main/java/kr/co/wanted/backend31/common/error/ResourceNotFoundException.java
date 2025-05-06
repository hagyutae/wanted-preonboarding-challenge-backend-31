package kr.co.wanted.backend31.common.error;

import java.util.Map;

import lombok.ToString;

@ToString(callSuper = true)
public class ResourceNotFoundException extends BaseException {

    public ResourceNotFoundException() {
        super(ErrorCode.RESOURCE_NOT_FOUND);
    }

    public ResourceNotFoundException(Map<String, Object> detail) {
        super(ErrorCode.RESOURCE_NOT_FOUND, detail);
    }
    
    public ResourceNotFoundException(RuntimeException e) {
        super(ErrorCode.RESOURCE_NOT_FOUND, e);
    }

    public ResourceNotFoundException(Map<String, Object> detail, RuntimeException e) {
        super(ErrorCode.RESOURCE_NOT_FOUND, detail, e);
    }
}
