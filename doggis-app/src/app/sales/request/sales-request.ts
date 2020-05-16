export class SalesRequest {
    saleProduct: Array<SalesItemRequest>;

    constructor(saleItemsRequest: Array<SalesItemRequest>) {
        this.saleProduct = saleItemsRequest;
    }
}

export class SalesItemRequest {
    productId: Number;
    amount: number;

    constructor(productId: Number, amount: number) {
        this.productId = productId;
        this.amount = amount;
    }
}