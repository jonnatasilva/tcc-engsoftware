import { FormGroup, Validators, FormControl } from '@angular/forms';
import { ProductsResponse } from '../response/products-response';

export class ProductForm extends FormGroup {

    constructor() {
        super({
            productId: new FormControl({ value: 0, disabled: true }),
            name: new FormControl({ value: '', disabled: false }, [Validators.required, Validators.minLength(5)]),
            manufacturer: new FormControl({ value: 0, disabled: false }, [Validators.required, Validators.minLength(5)]),
            specifications: new FormControl({ value: '', disabled: false }, [Validators.required])
        });
    }

    get productId() {
        return this.get('productId');
    }

    get name() {
        return this.get('name');
    }

    get manufacturer() {
        return this.get('manufacturer');
    }

    get specifications() {
        return this.get('specifications');
    }

    setFromProductResponse(productResponse: ProductsResponse) {
        this.productId.setValue(productResponse.productId);
        this.name.setValue(productResponse.name); 
        this.manufacturer.setValue(productResponse.manufacturer.manufacturerId);       
        this.specifications.setValue(productResponse.specifications);
    }
}
