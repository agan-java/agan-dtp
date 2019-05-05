请求体格式
{
    "args": {
        "page":1,
        "size":10,
        "search":{
            "property1_eq":111,
            "property2_ne":"aaa",
            "property3_gt":333,
            "property4_gte":"bbb",
            "property5_lt":555,
            "property6_lte":"ccc",
            "property7_in":[7,77,777],
            "property8_notIn":["d","dd","ddd"],
            "property9_like":"eee",
            "property10_notLike":"fff"
        },
        "sort":["property11_asc","property12_desc"],
        "fields":["property1","property2","property3"]
    }
}
page为分页的页码；
size为分页的大小；
search为高级查询对象，为键-值格式。key格式为“属性名_操作符”；
sort为排序对象，为字符串数组格式，可以传入多个属性支持多级排序。格式为“属性名_排序符”；
fields为属性筛选，为字符串数组格式，传入需要的属性名；
属性名是对应实体bean的属性名，统一为驼峰格式，注意并非数据库字段名；
操作符支持10种类型：eq（=），ne（!=），gt（>），gte（>=），lt（<），lte（<=），in（in），notIn（not in），like（like），notLike（not like）；
排序符分为两种：asc（升序），desc（降序）。
操作符为空时，默认是eq；排序符为空时，默认是asc。（操作符或排序符为空时，不需要用“_”）
高级查询对象API
构造器com.agan.common.search.ExampleBuilder
查询对象com.agan.common.search.entity.Search
排序对象com.agan.common.search.entity.Sort
属性筛选对象com.agan.common.search.entity.Fields
操作符com.agan.common.search.entity.SearchOperator
构建

Example example = new ExampleBuilder(MyEntity.class)
                .search(search)
                .sort(sort)
                .fields(fields)
                .build();
使用

Page<MyEntity> entites = (Page<MyEntity>) myMapper.selectByExampleAndRowBounds(example, new RowBounds(page, size));


 List<MyEntity> entites = myMapper.selectByExample(example);
查询接口响应格式
find为单个查询
query为列表查询
queryPage为分页查询
查询接口前缀必须为以上3种，不可以个性化定义。
单个查询响应格式

{
    "result": {
        "property1": 111,
        "property2": "222",
        "property3": true
    },
    "status": 200
}
列表查询响应格式

{
    "result": [
        {
            "property1": 111,
            "property2": "222",
            "property3": true
        },
        {
            "property1": 333,
            "property2": "444",
            "property3": false
        }
    ],
    "status": 200
}
分页查询响应格式

{
    "result": {
        "rows": [
            {
                "property1": 111,
                "property2": "222",
                "property3": true
            },
            {
                "property1": 333,
                "property2": "444",
                "property3": false
            }
        ],
        "total": 99
    },
    "status": 200
}
