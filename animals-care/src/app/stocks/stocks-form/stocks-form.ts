import { FormGroup, FormControl, Validators } from '@angular/forms';
import { StockResponse } from 'src/app/provider/response/stock-response';

export class StockForm extends FormGroup {

    constructor() {
        super({
            stockId: new FormControl({ value: 0, disabled: true}),
            product: new FormControl({ value: undefined, disabled: false }, [Validators.required]),
            amount: new FormControl({ value: 1, disabled: false}, [Validators.required, Validators.min(1)])
        });
    }

    get stockId() {
        return this.get('stockId');
    }

    get product() {
        return this.get('product');
    }
    
    get amount() {
        return this.get('amount');
    }

    public setFromStockResponse(stockResponse: StockResponse) {
        this.stockId.setValue(stockResponse.stockId);
        this.product.setValue(stockResponse.product.productId);
        this.amount.setValue(stockResponse.amount);
        this.amount.setValidators([Validators.required, Validators.min(stockResponse.amount)]);
    }
}
