package cn.vko.zuoye.keyvalue;

public enum SysCodeType {

	LEARN_PHASE(7, "学段", false, null), //学习阶段,即学段
	GRADE(1, "年级", false, LEARN_PHASE), //
	SUBJECT(2, "学科", false, LEARN_PHASE), //

	BOOK_CATEGORY(3, "书籍类别", false, null), //
	BOOK_VERSION(4, "书籍版本", false, null), //
	KNOWLEDGE_MOUDLE(5, "知识模块,对应v3知识树", false, null), //
	KNOWLEGED(6, "知识点,对应v3知识树", false, null), //

	AREA(8, "地区", true, null), //
	YEAR(9, "年份", true, null), //
	PAPER_VERSION(10, "试卷版本", true, null), //
	PAPER_TYPE(11, "试卷类型", true, null), //
	EXAM_TYPE(12, "试题类型", true, SUBJECT), //
	DIFF(13, "试题难度", true, null), //
	K1(14, "V6知识树1级", false, SUBJECT), //
	K2(15, "V6知识树2级", false, K1), //
	K3(16, "V6知识树3级", false, K2), //
	SUBJECT_STRONG(20, "强项标签", false, null), //
	SUBJECT_WEAK(21, "弱项标签", false, null), //
	GROUP_GAME(80, "我爱游戏", false, null), //
	GROUP_INTEREST(81, "兴趣爱好", false, null), //
	GROUP_LIFE(82, "生活休闲", false, null), //
	GROUP_FEELING(83, "星座情感", false, null), //
	GROUP_STAR(84, "明星八卦", false, null), //
	GROUP_COSPLAY(85, "动漫COSPLAY", false, null), //
	GROUP_CLASS(86, "班级群", false, null), //
	GROUP_OTHER(87, "其它群标签", false, null);//

	private int type;
	private String description;
	private boolean manager;
	private SysCodeType parent;

	private SysCodeType(final int type, final String description, final boolean manager, final SysCodeType parent) {
		this.type = type;
		this.description = description;
		this.manager = manager;
		this.parent = parent;
	}

	public int type() {
		return type;
	}

	public String key() {
		return String.valueOf(type);
	}

	public String value() {
		return description;
	}

	public boolean isManager() {
		return manager;
	}

	public SysCodeType parent() {
		return parent;
	}
}
