package sample.challengewanted.dto.option;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * sample.challengewanted.dto.option.QOptionGroupResponse is a Querydsl Projection type for OptionGroupResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QOptionGroupResponse extends ConstructorExpression<OptionGroupResponse> {

    private static final long serialVersionUID = -1879832287L;

    public QOptionGroupResponse(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> name, com.querydsl.core.types.Expression<Integer> displayOrder, com.querydsl.core.types.Expression<? extends java.util.List<OptionResponse>> options) {
        super(OptionGroupResponse.class, new Class<?>[]{long.class, String.class, int.class, java.util.List.class}, id, name, displayOrder, options);
    }

}

