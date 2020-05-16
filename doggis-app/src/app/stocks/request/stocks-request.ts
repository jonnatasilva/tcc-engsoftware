export class StocksRequest {
    private product: StocksProductsRequest = new StocksProductsRequest();   
    amount: Number;

    set productId(productId: Number) {
        this.product.productId = productId;
    }
}

class StocksProductsRequest {
    productId: Number;
}
