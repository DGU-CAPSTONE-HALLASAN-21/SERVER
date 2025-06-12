package org.dgu.dto.autocomplete;

import java.util.List;
import org.dgu.dto.autocomplete.vo.Choice;

public record ResAutoComplete(
        List<Choice> choices
) {
}
