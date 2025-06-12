package org.dgu.dto.autocomplete;

import java.util.List;
import lombok.Builder;
import org.dgu.dto.autocomplete.vo.Message;

@Builder
public record ReqAutoComplete(
        List<Message> messages,
        String model,
        Boolean stream,
        Boolean store
) {

}

