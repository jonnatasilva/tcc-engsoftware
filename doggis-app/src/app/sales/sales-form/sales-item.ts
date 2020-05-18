export class SalesItem {
    productId: Number;
    productName: String;
    productPrice: number;
    amount: number;

    constructor(productId: Number, productName: String, productPrice: number, amount: number) {
        this.productId = productId
        this.productName = productName;
        this.productPrice = productPrice;
        this.amount = amount;
    }
}
