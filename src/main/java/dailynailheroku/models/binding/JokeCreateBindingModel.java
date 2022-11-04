package dailynailheroku.models.binding;

import javax.validation.constraints.Size;

public class JokeCreateBindingModel {
    private String text;

    public JokeCreateBindingModel() {
    }

    @Size(min = 15, max = 1000, message = "Text must be between 15 and 1000 characters")
    public String getText() {
        return text;
    }

    public JokeCreateBindingModel setText(String text) {
        this.text = text;
        return this;
    }

}
