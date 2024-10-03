declare module "*.scss?inline" {
  import { CSSResultGroup } from "lit";
  const styles: CSSResultGroup;
  export default styles;
}
