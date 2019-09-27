import { FormGroup, FormControl, Validators } from '@angular/forms';

export class SaleForm extends FormGroup {

    constructor() {
        super({
            product: new FormControl({ value: undefined, disabled: false }, Validators.required),
            amount: new FormControl({ value: 1, disabled: false }, [Validators.required, Validators.min(1)])
        })
    }

    get product() {
        return this.get('product');
    }

    get amount() {
        return this.get('amount');
    }
}
