import {html, LitElement, unsafeCSS} from "lit";
import {customElement, property} from "lit/decorators.js";
import styles from "./button.component.scss?inline";

export type MyButtonVariant = "primary" | "outline" | "danger";

@customElement("my-button")
export class MyButton extends LitElement {
    static styles = unsafeCSS(styles);

    @property()
    variant: MyButtonVariant = "primary";

    @property()
    label: string = "Button";

    override render() {
        return html`
            <button type="button" class="my-button ${this.variant}">
                ${this.label}
            </button>
        `;
    }
}