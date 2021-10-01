package com.example.lepaking.network

/**
 * Created by iChris on 9/14/17.
 */

class EndpointPath {
    // Default URL will be as followed
    // https://api.smartsales.verismartsoft.biz/api/v1.0/products
    companion object {
        private const val ROUTE_PREFIX = "api"
        private const val API_VERSION: String = "v1.0"
        private const val PATH = "/"

        const val LOGIN : String = ROUTE_PREFIX + PATH + "Login"
        const val LOGOUT : String = ROUTE_PREFIX + PATH + "Logout"
        const val TOKEN : String = ROUTE_PREFIX + PATH + "Token"

        const val PRODUCT : String = ROUTE_PREFIX + PATH + API_VERSION + PATH + "Products"
        const val RECEIPT : String = ROUTE_PREFIX + PATH +  API_VERSION + PATH + "Receipt"

        const val REFERENCE_ADDRESS : String = ROUTE_PREFIX + PATH + API_VERSION + PATH + "AddressReferences"
        const val REFERENCE_ASSET : String = ROUTE_PREFIX + PATH + API_VERSION + PATH + "AssetReferences"
        const val REFERENCE_CUSTOMER : String = ROUTE_PREFIX + PATH + API_VERSION + PATH + "CustomerReferences"
        const val REFERENCE_OTHER : String = ROUTE_PREFIX + PATH + API_VERSION + PATH + "OtherReferences"
        const val REFERENCE_PAYMENT : String = ROUTE_PREFIX + PATH + API_VERSION + PATH + "PaymentReferences"
        const val REFERENCE_PRODUCT : String = ROUTE_PREFIX + PATH + API_VERSION + PATH + "ProductReferences"
        const val REFERENCE_TRANSACTION : String = ROUTE_PREFIX + PATH + API_VERSION + PATH + "TransactionReferences"
        const val REFERENCE_VISIT : String = ROUTE_PREFIX + PATH + API_VERSION + PATH + "VisitReferences"

        const val TRANSACTION_VISIT : String = ROUTE_PREFIX + PATH +  API_VERSION + PATH + "Visits"
        const val TRANSACTION_VISIT_IMAGE : String = ROUTE_PREFIX + PATH +  API_VERSION + PATH + "Visits" + PATH + "images"
        const val TRANSACTION_SALES_ORDER : String = ROUTE_PREFIX + PATH +  API_VERSION + PATH + "SalesOrders"
        const val TRANSACTION_SURVEY : String = ROUTE_PREFIX + PATH +  API_VERSION + PATH + "Surveys"
        const val TRANSACTION_SALES_INVOICE : String = ROUTE_PREFIX + PATH +  API_VERSION + PATH + "Invoices"
        const val TRANSACTION_COLLECTION : String = ROUTE_PREFIX + PATH +  API_VERSION + PATH + "Collections"
        const val TRANSACTION_COLLECTION_IMAGE : String = ROUTE_PREFIX + PATH +  API_VERSION + PATH + "Collections" + PATH + "images"
        const val TRANSACTION_CREDIT_NOTE : String = ROUTE_PREFIX + PATH +  API_VERSION + PATH + "CreditNotes"
        const val TRANSACTION_RETURN_INVOICE : String = ROUTE_PREFIX + PATH +  API_VERSION + PATH + "ReturnInvoices"
        const val TRANSACTION_STOCK_TAKE : String = ROUTE_PREFIX + PATH +  API_VERSION + PATH + "StockTakes"
        const val TRANSACTION_ASSET_CHECK : String = ROUTE_PREFIX + PATH +  API_VERSION + PATH + "AssetChecks"
        const val TRANSACTION_ASSET_CHECK_IMAGE : String = ROUTE_PREFIX + PATH +  API_VERSION + PATH + "AssetChecks" + PATH + "images"
        const val TRANSACTION_EXPENSE : String = ROUTE_PREFIX + PATH +  API_VERSION + PATH + "Expenses"
        const val TRANSACTION_EXPENSE_IMAGE : String = ROUTE_PREFIX + PATH +  API_VERSION + PATH + "Expenses" + PATH + "images"


        const val INVENTORY_STOCK_ADJUSTMENT : String = ROUTE_PREFIX + PATH +  API_VERSION + PATH + "StockAdjustments"
        const val INVENTORY_STOCK_TRANSFER : String = ROUTE_PREFIX + PATH +  API_VERSION + PATH + "StockTransfers"
        const val INVENTORY_STOCK_TRANSFER_ID : String = ROUTE_PREFIX + PATH +  API_VERSION + PATH + "StockTransfers"  + PATH + "GetStockTransferById"
        const val INVENTORY_STOCK_ADJUSTMENT_ID : String = ROUTE_PREFIX + PATH +  API_VERSION + PATH + "StockAdjustments"  + PATH + "GetStockAdjustmentById"
        const val INVENTORY_VAN : String = ROUTE_PREFIX + PATH +  API_VERSION + PATH + "Inventories"

        const val MAINTENANCE_ASSET : String = ROUTE_PREFIX + PATH + API_VERSION + PATH + "assets"
        const val MAINTENANCE_CUSTOMER : String = ROUTE_PREFIX + PATH +  API_VERSION + PATH + "Customers"
        const val MAINTENANCE_CUSTOMER_IMAGE : String = ROUTE_PREFIX + PATH +  API_VERSION + PATH + "Customers" + PATH + "images"
        const val MAINTENANCE_DATAFILE : String = ROUTE_PREFIX + PATH + API_VERSION + PATH + "dataFiles"
        const val MAINTENANCE_DEPOSITORIES : String = ROUTE_PREFIX + PATH + API_VERSION + PATH + "depositories"
        const val MAINTENANCE_DISTRIBUTORS : String = ROUTE_PREFIX + PATH + API_VERSION + PATH + "distributors"
        const val MAINTENANCE_MESSAGE : String = ROUTE_PREFIX + PATH + API_VERSION + PATH + "messages"
        const val MAINTENANCE_MUST_SALES : String = ROUTE_PREFIX + PATH + API_VERSION + PATH + "mustSells"
        const val MAINTENANCE_NON_SALES_DAYS : String = ROUTE_PREFIX + PATH + API_VERSION + PATH + "nonSalesDays"
        const val MAINTENANCE_PLANS : String = ROUTE_PREFIX + PATH + API_VERSION + PATH + "plans"
        const val MAINTENANCE_PRODUCT : String = ROUTE_PREFIX + PATH + API_VERSION + PATH + "products"
        const val MAINTENANCE_PRODUCT_IMAGE : String = ROUTE_PREFIX + PATH + API_VERSION + PATH + "products" + PATH + "images"
        const val MAINTENANCE_PROMOTION : String = ROUTE_PREFIX + PATH + API_VERSION + PATH + "promotions"
        const val MAINTENANCE_QUESTIONNAIRES : String = ROUTE_PREFIX + PATH + API_VERSION + PATH + "questionnaires"
        const val MAINTENANCE_QUESTION : String = ROUTE_PREFIX + PATH + API_VERSION + PATH + "questions"
        const val MAINTENANCE_SCHEDULES : String = ROUTE_PREFIX + PATH + API_VERSION + PATH + "schedules"
        const val MAINTENANCE_USER : String = ROUTE_PREFIX + PATH + API_VERSION + PATH + "users"
        const val MAINTENANCE_VOUCHERS : String = ROUTE_PREFIX + PATH + API_VERSION + PATH + "vouchers"

        const val DATABASE_BACKUP : String = ROUTE_PREFIX + PATH + API_VERSION + PATH + "DatabaseBackup"

        const val GENERAL_RECORD_TYPE : String = ROUTE_PREFIX + PATH + API_VERSION + PATH + "recordTypes"
        const val GENERAL_CONFIGURATIONS : String = ROUTE_PREFIX + PATH + API_VERSION + PATH + "Configurations"

        const val APK_DOWNLOAD : String = ROUTE_PREFIX + PATH + API_VERSION + PATH + "ApkUpdater" + PATH + "apkdownload"
    }
}
