export class SalesItem {
    productId: Number;
    productName: String;
    amount: number;

    constructor(productId: Number, productName: String, amount: number) {
        this.productId = productId;
        this.productName = productName;
        this.amount = amount;
    }
}
