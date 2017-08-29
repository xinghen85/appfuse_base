package com.btxy.basis.common;

public class ContantBusiness {
	 /******模板类型******/
    public static final String TPL_TYPE_TEST = "AYA";//测试模板
    public static final String TPL_TYPE_12 = "AY12";//12
    /******生成报告类型******/
    public static final String REPORT_TYPE_WORD = "9FA";//WORD报告
    public static final String REPORT_TYPE_HTML = "9FB";//HTML报告
    public static final String REPORT_TYPE_PDF = "9FC";//PDF报告
    /******函数类型******/
    public static final String FUN_TYPE_SYSTEM_JAVA = "9AA";//系统JAVA函数
    public static final String FUN_TYPE_OFFICE_TABLE = "9AB";//OFFICE表函数
    public static final String FUN_TYPE_OFFICE_CHART = "9AC";//OFFICE图函数
    public static final String FUN_TYPE_FREEMARKER = "9AD";//FREEMARKER函数
    /******函数参数类型******/
    public static final String FUN_PARAM_TYPE_BOOLEAN = "9BA";//布尔型
    public static final String FUN_PARAM_TYPE_LONG = "9BB";//整型
    public static final String FUN_PARAM_TYPE_DOUBLE = "9BC";//浮点型
    public static final String FUN_PARAM_TYPE_STRING = "9BD";//字符串
    public static final String FUN_PARAM_TYPE_ENUM = "9BE";//枚举
    public static final String FUN_PARAM_TYPE_FIXED_PROPERTY = "9BP";//系统可变属性
    public static final String FUN_PARAM_TYPE_CACULATE = "9BM";//计算项
    public static final String FUN_PARAM_TYPE_DIMENSION = "9BF";//维度
    public static final String FUN_PARAM_TYPE_TOPIC = "9BG";//题目
    public static final String FUN_PARAM_TYPE_TOPIC_OPTION = "9BH";//题目选项
    public static final String FUN_PARAM_TYPE_TABLE = "9BI";//统计表
    public static final String FUN_PARAM_TYPE_COLUMN = "9BJ";//统计列
    public static final String FUN_PARAM_TYPE_ONE_DIMENSIONA_ARRAY = "9BQ";//一维行列对象
    public static final String FUN_PARAM_TYPE_TWO_DIMENSIONA_ARRAY = "9BK";//二维行列对象
    public static final String FUN_PARAM_TYPE_MULITI_DIMENSIONA_ARRAY = "9BN";//多维行列对象
    public static final String FUN_PARAM_TYPE_JSON_OBJECT = "9BL";//JSON对象
    public static final String FUN_PARAM_TYPE_CHART_STYLE = "9BO";//图表样式
    /******函数返回值类型******/
    public static final String FUN_RETURN_TYPE_BOOLEAN = "9CA";//布尔型
    public static final String FUN_RETURN_TYPE_LONG = "9CB";//整型
    public static final String FUN_RETURN_TYPE_DOUBLE = "9CC";//浮点型
    public static final String FUN_RETURN_TYPE_STRING = "9CD";//字符串
    public static final String FUN_RETURN_TYPE_ONE_DIMENSIONA_ARRAY = "9CE";//一级行列对象
    public static final String FUN_RETURN_TYPE_TWO_DIMENSIONA_ARRAY = "9CF";//二级行列对象
    public static final String FUN_RETURN_TYPE_JSON = "9CG";//JSON对象
    public static final String FUN_RETURN_TYPE_VBA_TABLE = "9CH";//VBA表代码
    public static final String FUN_RETURN_TYPE_VBA_CHART = "9CI";//VBA图代码
    /******统计数据状态******/
    public static final String STAT_DATA_STATUS_NEW = "3FA";//新建
    public static final String STAT_DATA_STATUS_COLLECT_PAPERINFO_ING = "3FB";//正在收集问卷信息
    public static final String STAT_DATA_STATUS_COLLECT_PAPERINFO_END = "3FC";//收集问卷信息完成
    public static final String STAT_DATA_STATUS_COLLECT_QUIZINFO_ING = "3FD";//收集测评数据并算分中
    public static final String STAT_DATA_STATUS_COLLECT_QUIZINFO_END = "3FE";//收集测评数据并算分完毕
    public static final String STAT_DATA_STATUS_DATA_STAT_ING = "3FF";//数据统计中
    public static final String STAT_DATA_STATUS_DATA_STAT_END = "3FG";//数据统计完成
    
    /******审读问题状态******/
    public static final String status_NEW = "7BA";//新建
    public static final String status_UNDERWAY = "7BB";//进行中
    public static final String status_FINISHED = "7BC";//已解决
    public static final String status_FEEDBACK = "7BD";//反馈
    public static final String status_CLOSED = "7BE";//已关闭
    /******统计列类型******/
    public static final String STAT_COLUMN_OBJECT = "BAA";//对象
    public static final String STAT_COLUMN_OBJECT_ATTRIBUTE = "BAB";//对象属性
    public static final String STAT_COLUMN_QUESTION = "BAC";//问题
    /******题目状态******/
    public static final String QUESTION_STATUS_NEW = "BCA";//新建
    public static final String QUESTION_STATUS_NORMAL = "BCB";//可用
    public static final String QUESTION_STATUS_IN_USE = "BCC";//在用
    public static final String QUESTION_STATUS_DELETE = "BCD";//删除
    /******对象属性类型******/
    public static final String OBJECT_ATT_TYPE_OBJECT = "BDA";//对象
    public static final String OBJECT_ATT_TYPE_NAME = "BDB";//名称
    public static final String OBJECT_ATT_TYPE_FIXED_PROPERTY = "BDC";//系统可变属性
    public static final String OBJECT_ATT_TYPE_VALUE = "BDD";//文本或数值
    /******对象索引类型******/
    public static final String OBJECT_INDEX_TYPE_UNIQUE = "BEA";//唯一索引
    public static final String OBJECT_INDEX_TYPE_COMMON = "BEB";//普通索引
    /******任务类型******/
    public static final String TASK_TYPE_OBJECT_INDEX_REBUILD = "BFA";//对象索引重建
    public static final String TASK_TYPE_OBJECT_IMPORT = "BFB";//导入对象
    public static final String TASK_TYPE_QUESTION_IMPORT = "BFC";//题目导入
    public static final String TASK_TYPE_DATA_STAT = "BFD";//数据统计
    public static final String TASK_TYPE_DATA_TPL_HANDLE = "BF1";//模板处理
    public static final String TASK_TYPE_DATA_REPORT_GEN = "BF2";//生成报告VBA
    public static final String TASK_TYPE_DATA_RPT_INST_GEN = "BF3";//生成报告实例
    public static final String TASK_TYPE_DATA_RPT_FUN_GEN = "BF4";//生成报告函数实例
    public static final String TASK_TYPE_DATA_RPT_FUN_EXEC = "BF5";//执行报告函数实例
    public static final String TASK_TYPE_DATA_RPT_CHART = "BF6";//生成图表
    public static final String TASK_TYPE_DATA_RPT_VBA_RUN = "BF7";//执行VBA生成报告
    
    
    /******任务状态******/
    public static final String TASK_SERVER_TYPE_ADMIN_SERVER = "BYA";//管理服务端
    public static final String TASK_SERVER_TYPE_REPORT_SERVER = "BYB";//报告服务端
    
    /******测评类别******/
    public static final String QUIZ_TYPE_ONLINE = "3BA";//电子测评
    public static final String QUIZ_TYPE_OFFLINE = "3BB";//纸质测评
    
    /******题目类型******/
    public static final String QUESTION_TYPE_SELECT_BOOLEAN = "1HA";//判断题
    public static final String QUESTION_TYPE_SELECT_ONE = "1HB";//单选题
    public static final String QUESTION_TYPE_SELECT_MULIT = "1HC";//多选题
    public static final String QUESTION_TYPE_SELECT_LKT = "1HD";//里克特题
    public static final String QUESTION_TYPE_FILL_TEXT = "1HE";//填空题
    public static final String QUESTION_TYPE_ANSWER = "1HF";//问答题
    public static final String QUESTION_TYPE_SORT = "1HG";//排序题
    public static final String QUESTION_TYPE_LINE = "1HK";//连线题
    public static final String QUESTION_TYPE_UNDERSTAND = "1HL";//阅读理解题
    public static final String QUESTION_TYPE_HXT = "1HM";//划消题
    public static final String QUESTION_TYPE_TEXT = "1HN";//文本
    public static final String QUESTION_TYPE_SELECT_XLXZ = "1HP";//下拉选择题
    
    /******模板状态******/
    public static final String TPL_STATUS_NEW = "9DA";//新建
	public static final String TPL_STATUS_HANDLE = "9DB";//翻译中
	public static final String TPL_STATUS_HANDLE_FAILRE = "9DC";//翻译失败
	public static final String TPL_STATUS_HANDLE_SUCCESS = "9DD";//翻译成功
	
	/******模板类型******/
    public static final String TPL_TYPE_WORD = "9EA";//WORD
	public static final String TPL_TYPE_HTML = "9EB";//HTML
	public static final String TPL_TYPE_PPT = "9EC";//PPT
	
	/******报告类型******/
    public static final String RPT_TYPE_WORD = "9FA";//WORD
	public static final String RPT_TYPE_HTML = "9FB";//HTML
	public static final String RPT_TYPE_PPT = "9FC";//PPT
	public static final String RPT_TYPE_PDF = "9FD";//PDF
	
	/******报告状态******/
    public static final String RPT_STATUS_NEW = "9LA";//新建
    public static final String RPT_STATUS_INST = "9LB";//正在生成报告实例
    public static final String RPT_STATUS_INST_SUCCESS = "9LC";//生成报告实例成功
    public static final String RPT_STATUS_INST_FAILURE = "9LD";//报告实例生成失败
    public static final String RPT_STATUS_FUN_INST = "9LK";//正在生成函数实例
    public static final String RPT_STATUS_FUN_INST_FAILURE = "9LL";//生成函数实例失败
    public static final String RPT_STATUS_FUN_INST_SUCCESS = "9LM";//生成函数实例成功
    public static final String RPT_STATUS_FUN = "9LE";//正在计算函数
    public static final String RPT_STATUS_FUN_SUCCESS = "9LF";//函数计算成功
    public static final String RPT_STATUS_FUN_FAILURE = "9LG";//函数计算失败
    public static final String RPT_STATUS_GEN = "9LH";//正在生成报告
    public static final String RPT_STATUS_GEN_SUCCESS = "9LI";//报告生成成功
    public static final String RPT_STATUS_GEN_FAILURE = "9LJ";//报告生成失败
    
    
    /******报告实例状态******/
    public static final String RPT_INST_STATUS_NEW = "9NA";//新建
    public static final String RPT_INST_STATUS_FUN_INST = "9NH";//正在生成函数实例
    public static final String RPT_INST_STATUS_FUN_INST_FAILURE = "9NI";//生成函数实例失败
    public static final String RPT_INST_STATUS_FUN_INST_SUCCESS = "9NJ";//生成函数实例成功
    public static final String RPT_INST_STATUS_EXEC_FUN = "9NB";//正在计算函数
    public static final String RPT_INST_STATUS_EXEC_FAILURE = "9NC";//函数计算失败
    public static final String RPT_INST_STATUS_EXEC_SUCCESS = "9ND";//函数计算成功
    public static final String RPT_INST_STATUS_REPORT = "9NE";//正在生成报告
    public static final String RPT_INST_STATUS_REPORT_FAILURE = "9NF";//报告生成失败
    public static final String RPT_INST_STATUS_REPROT_SUCCESS = "9NG";//报告生成成功
    
    /******报告函数实例状态******/
    public static final String RPT_FUN_INST_STATUS_EXEC_NEW = "9MA";//新建
    public static final String RPT_FUN_INST_STATUS_EXEC_FUN = "9MB";//正在计算函数
    public static final String RPT_FUN_INST_STATUS_EXEC_FAILURE = "9MC";//函数计算失败
    public static final String RPT_FUN_INST_STATUS_EXEC_SUCCESS = "9MD";//函数计算成功
    
    
    /******表达式******/
    public static final String EXPRESSION_TYPE_DAYU = "CBA";
    public static final String EXPRESSION_TYPE_DAYU_DENGYU = "CBB";
    public static final String EXPRESSION_TYPE_XIAOYU = "CBC";
    public static final String EXPRESSION_TYPE_XIAOYU_DENGYU = "CBD";
    public static final String EXPRESSION_TYPE_DENGYU = "CBE";
    public static final String EXPRESSION_TYPE_NOT_DENGYU = "CBF";
    
    /******审读类型******/
    public static final String CKB_TYPE_DATA = "7CA";
    public static final String CKB_TYPE_REPORT = "7CB";
    public static final String CKB_TYPE_TEMPLATE = "7CC";
    
    /******范围标识*****/
    public static final String SCOPE_MARK = "s";
    public static final String CATALOG_MARK = "c";
    public static final String OPTION_INDEX = "i";
}
