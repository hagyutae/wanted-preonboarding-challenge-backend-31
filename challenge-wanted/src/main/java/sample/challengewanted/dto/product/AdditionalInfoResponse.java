package sample.challengewanted.dto.product;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdditionalInfoResponse {
    private Boolean assemblyRequired;
    private String assemblyTime;

    @QueryProjection
    public AdditionalInfoResponse(Boolean assemblyRequired, String assemblyTime) {
        this.assemblyRequired = assemblyRequired;
        this.assemblyTime = assemblyTime;
    }
}