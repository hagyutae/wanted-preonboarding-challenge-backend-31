package lkdcode.wanted.ecommerce.modules.products.application.ports.out.option.command;

import lkdcode.wanted.ecommerce.modules.products.application.usecase.option.command.CommandOptionResult;
import lkdcode.wanted.ecommerce.modules.products.domain.model.option.AddOptionModel;

public interface AddOptionOutPort {
    CommandOptionResult add(AddOptionModel model);
}
