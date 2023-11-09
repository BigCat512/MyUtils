package org.example.common.utils.excel;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 级联下拉示例
 * </p>
 *
 * @author 林炯潮
 * @version 1.0
 * @since 2023/11/7
 */
// @Component
@Slf4j
public class SaleRecordExcelGenerateImportServiceImpl/*  implements ApaasExcelGenerateImportService */ {
    //
    // private static final List<String> BILL_CODE_LIST = new ArrayList<>();
    //
    // @Resource
    // private ProjectService projectService;
    // @Resource
    // private CustomerService customerService;
    // @Resource
    // private ShopService shopService;
    // @Resource
    // private StoreContractService storeContractService;
    //
    // static {
    //     BILL_CODE_LIST.add(EBill.SALES_RECORD.code());
    // }
    //
    // @Override
    // public boolean validBillCode(String billCode) {
    //     return BILL_CODE_LIST.contains(billCode);
    // }
    //
    // @Override
    // public List<String> getImportDataList(BillBO bill, String param) {
    //     return Collections.emptyList();
    // }
    //
    // @Override
    // public List<WriteHandler> getWriteHandlerList(BillBO billBO, List<SdkExcelGenerateImportBO> sdkExcelGenerateImportList) {
    //     List<WriteHandler> writeHandlerList = new ArrayList<>();
    //     String groupCode = UserTool.getGroupCode();
    //     for (SdkExcelGenerateImportBO sdkExcelGenerateImportBO : sdkExcelGenerateImportList) {
    //         String param = sdkExcelGenerateImportBO.getParam();
    //         List<String> options = sdkExcelGenerateImportBO.getOptions();
    //         if (CollectionUtil.isEmpty(options)) {
    //             return writeHandlerList;
    //         }
    //         switch (param) {
    //             // 1. projectName 与 merchantName的映射关系
    //             case "projectName": {
    //                 Map<String, String> projectKvMap = projectService.lambdaQuery()
    //                         .select(Project::getCode, Project::getName)
    //                         .in(Project::getName, options)
    //                         .eq(Project::getGroupCode, groupCode).list()
    //                         .stream().collect(Collectors.toMap(Project::getCode, Project::getName, (o1, o2) -> o2));
    //                 if (MapUtil.isEmpty(projectKvMap)) {
    //                     return writeHandlerList;
    //                 }
    //                 List<CustomerPO> customerList = customerService.lambdaQuery()
    //                         .eq(CustomerPO::getGroupCode, groupCode)
    //                         .in(CustomerPO::getProjectCode, projectKvMap.keySet()).list();
    //                 writeHandlerList.add(getWriteHandler(sdkExcelGenerateImportBO, projectKvMap, JSONUtil.parseArray(customerList), "projectCode", "name"));
    //                 break;
    //             }
    //             // 2. merchantName 与 storeNameByImport的映射关系
    //             case "merchantName": {
    //                 Map<String, String> customerKvMap = customerService.lambdaQuery()
    //                         .select(CustomerPO::getCode, CustomerPO::getName)
    //                         .in(CustomerPO::getName, options)
    //                         .eq(CustomerPO::getGroupCode, groupCode).list()
    //                         .stream().collect(Collectors.toMap(CustomerPO::getCode, CustomerPO::getName, (o1, o2) -> o2));
    //                 if (MapUtil.isEmpty(customerKvMap)) {
    //                     return writeHandlerList;
    //                 }
    //                 List<Shop> shopList = shopService.lambdaQuery()
    //                         .eq(Shop::getGroupCode, groupCode)
    //                         .in(Shop::getCustomerCode, customerKvMap.keySet()).list();
    //                 writeHandlerList.add(getWriteHandler(sdkExcelGenerateImportBO, customerKvMap, JSONUtil.parseArray(shopList), "customerCode", "name"));
    //                 break;
    //             }
    //
    //             // 3. 处理 storeNameByImport 与 brand 的映射关系
    //             case "storeNameByImport": {
    //                 Map<String, String> shopKvMap = shopService.lambdaQuery()
    //                         .select(Shop::getCode, Shop::getName)
    //                         .in(Shop::getName, options)
    //                         .eq(Shop::getGroupCode, groupCode).list()
    //                         .stream().collect(Collectors.toMap(Shop::getCode, Shop::getName, (o1, o2) -> o2));
    //
    //                 if (MapUtil.isEmpty(shopKvMap)) {
    //                     return writeHandlerList;
    //                 }
    //                 List<StoreContractPO> storeContractList = storeContractService.lambdaQuery()
    //                         .eq(StoreContractPO::getGroupCode, groupCode)
    //                         .in(StoreContractPO::getShopCode, shopKvMap.keySet())
    //                         .list();
    //                 writeHandlerList.add(getWriteHandler(sdkExcelGenerateImportBO, shopKvMap, JSONUtil.parseArray(storeContractList), "shopCode", "brandName"));
    //                 break;
    //             }
    //         }
    //     }
    //     return writeHandlerList;
    // }
    //
    // public WriteHandler getWriteHandler(SdkExcelGenerateImportBO sdkExcelGenerateImportBO, Map<String, String> keyCodeNameMap, JSONArray dataArray, String dataKeyParamName, String dataValueParamName) {
    //     // 处理 大小集之间的映射关系
    //     Map<String, List<String>> siteMap = new HashMap<>();
    //     dataArray.forEach(item -> {
    //         JSONObject data = JSONUtil.parseObj(item);
    //         String key = keyCodeNameMap.get(data.getStr(dataKeyParamName));
    //         String value = data.getStr(dataValueParamName);
    //         List<String> relationList = siteMap.getOrDefault(key, new ArrayList<>());
    //         if (StringUtil.isNotBlank(value) && !relationList.contains(value)) {
    //             relationList.add(value);
    //         }
    //         siteMap.put(key, relationList);
    //     });
    //     return new CascadeWriteHandler(dataKeyParamName + dataValueParamName, sdkExcelGenerateImportBO.getIndex(), sdkExcelGenerateImportBO.getOptions(), siteMap);
    // }
}
