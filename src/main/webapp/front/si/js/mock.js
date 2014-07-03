module.exports = function (app) {
	/** 公用模块 **/
		//获取服务端时间
		app.get('/oms/api/getServerTime', function(req, res){
			res.json({
				"status" : "ok",
				"message" : "",
				"data" : {
					"serverTime" : 1402557950677   //系统当前时间
				}
			});
		});

		//公共接口（根据传递的参数返回不同的类目选项）
		app.get('/si/common/getbaseinfo', function(req, res){
			res.json({
				"status" : "ok",
				"message" : "",
				"data" : {
					"paymentLocationList" : [//缴纳地
						{
							"id" : 0,
							"name" : "上海"
						},
						{
							"id" : 1,
							"name" : "深圳"
						},
						{
							"id" : 2,
							"name" : "宁波"
						}
					],
					"socialCardList" : [//社保卡
						{
							"id" : 0,
							"name" : "无"
						},
						{
							"id" : 1,
							"name" : "有"
						}
					],
					"applyFormList" : [//异地申请表
						{
							"id" : 0,
							"name" : "无"
						},
						{
							"id" : 1,
							"name" : "有"
						}
					],
					"censusList" : [ //户籍性质
						{
							"id" : 0,
							"name" : "外地农村"
						},
						{
							"id" : 1,
							"name" : "外地城镇"
						},
						{
							"id" : 3,
							"name" : "上海城镇"
						},
						{
							"id" : 4,
							"name" : "上海农村"
						}
					],
					"titleLevelDegreeList" : [//职等职级
						{
	                        "id":"x1",
	                        "text":"行政职系",
	                        "children":[
	                            {
	                                "id":"d9",
	                                "text":"1 助理/专员",
	                                "children":[
	                                    {
	                                        "id":"23",
	                                        "text":"1-1 初级助理"
	                                    },
	                                    {
	                                        "id":"24",
	                                        "text":"1-2 助理"
	                                    },
	                                    {
	                                        "id":"25",
	                                        "text":"1-3 高级助理"
	                                    },
	                                    {
	                                        "id":"26",
	                                        "text":"1-4 专员"
	                                    },
	                                    {
	                                        "id":"27",
	                                        "text":"1-5 高级专员"
	                                    }
	                                ]
	                            },
	                            {
	                                "id":"d8",
	                                "text":"2 主管",
	                                "children":[
	                                    {
	                                        "id":"18",
	                                        "text":"2-1 助理主管"
	                                    },
	                                    {
	                                        "id":"19",
	                                        "text":"2-2 主管"
	                                    },
	                                    {
	                                        "id":"20",
	                                        "text":"2-3 资深主管"
	                                    },
	                                    {
	                                        "id":"21",
	                                        "text":"2-4 高级主管"
	                                    },
	                                    {
	                                        "id":"22",
	                                        "text":"2-5 资深高级主管"
	                                    }
	                                ]
	                            },
	                            {
	                                "id":"d7",
	                                "text":"3 经理",
	                                "children":[
	                                    {
	                                        "id":"14",
	                                        "text":"3-1 经理"
	                                    },
	                                    {
	                                        "id":"15",
	                                        "text":"3-2 资深经理"
	                                    },
	                                    {
	                                        "id":"16",
	                                        "text":"3-3 高级经理"
	                                    },
	                                    {
	                                        "id":"17",
	                                        "text":"3-4 资深高级经理"
	                                    }
	                                ]
	                            },
	                            {
	                                "id":"d6",
	                                "text":"4 总监",
	                                "children":[
	                                    {
	                                        "id":"10",
	                                        "text":"4-1 总监"
	                                    },
	                                    {
	                                        "id":"11",
	                                        "text":"4-2 资深总监"
	                                    },
	                                    {
	                                        "id":"12",
	                                        "text":"4-3 高级总监"
	                                    },
	                                    {
	                                        "id":"13",
	                                        "text":"4-4 资深高级总监",
	                                        "children":null
	                                    }
	                                ]
	                           	},
	                           	{
	                           		"id":"d4",
	                           		"text":"5 事业部总经理/事业部财务总监",
	                           		"children":[
	                           			{
	                           				"id":"4",
	                           				"text":"5-1 事业部总经理",
	                           				"children":null
	                           			},
	                           			{
	                           				"id":"7",
	                           				"text":"5-1 事业部财务总监",
	                           				"children":null
	                           			},
	                           			{
	                           				"id":"8",
	                           				"text":"5-2 资深事业部总经理/资深事业部财务总监",
	                           				"children":null
	                           			},
	                           			{
	                           				"id":"5",
	                           				"text":"5-2 资深事业部总经理",
	                           				"children":null
	                           			},
	                           			{
	                           				"id":"6",
	                           				"text":"5-3 高级事业部总经理",
	                           				"children":null
	                           			},
	                           			{
	                           				"id":"9",
	                           				"text":"5-3 高级事业部财务总监",
	                           				"children":null
	                           			}
	                           		]
	                           	},
	                           	{
	                           		"id":"d2",
	                           		"text":"6 大区总裁/集团总监",
	                           		"children":[
	                           			{
	                           				"id":"2",
	                           				"text":"6-1 大区总裁",
	                           				"children":null
	                           			},
	                           			{
	                           				"id":"3",
	                           				"text":"6-1 集团总监",
	                           				"children":null
	                           			}
	                           		]
	                           	},
	                           	{
	                           		"id":"d1",
	                           		"text":"7 集团总裁",
	                           		"children":[
	                           			{
	                           				"id":"1",
	                           				"text":"7-1 集团总裁",
	                           				"children":null
	                           			}
	                           		]
	                           	}
	                        ]
	                    },
	                    {
	                    	"id":"x2",
	                    	"text":"营销职系",
	                    	"children":[
	                    		{
	                    			"id":"d19",
	                    			"text":"1 物业顾问/营业主任",
	                    			"children":[
	                    				{
	                    					"id":"47",
	                    					"text":"1-1 初级物业顾问",
	                    					"children":null
	                    				},
	                    				{
	                    					"id":"48",
	                    					"text":"1-2 物业顾问",
	                    					"children":null
	                    				},
	                    				{
	                    					"id":"49",
	                    					"text":"1-3 高级物业顾问",
	                    					"children":null
	                    				},
	                    				{
	                    					"id":"50",
	                    					"text":"1-4 营业主任",
	                    					"children":null
	                    				},
	                    				{
	                    					"id":"51",
	                    					"text":"1-5 高级营业主任",
	                    					"children":null
	                    				}
	                    			]
	                    		},
	                    		{
	                    			"id":"d17",
	                    			"text":"2 分行经理/分行主管",
	                    			"children":[
	                    				{
	                    					"id":"46",
	                    					"text":"2-1 分行主管",
	                    					"children":null
	                    				},
	                    				{
	                    					"id":"42",
	                    					"text":"2-2 分行经理",
	                    					"children":null
	                    				},
	                    				{
	                    					"id":"70",
	                    					"text":"2-3-1 高级经理1星",
	                    					"children":null
	                    				},
	                    				{
	                    					"id":"71",
	                    					"text":"2-3-2 高级经理2星",
	                    					"children":null
	                    				},
	                    				{
	                    					"id":"72",
	                    					"text":"2-3-3 高级经理3星",
	                    					"children":null
	                    				},
	                    				{
	                    					"id":"73",
	                    					"text":"2-3-4 高级经理4星",
	                    					"children":null
	                    				},
	                    				{
	                    					"id":"74",
	                    					"text":"2-3-5 高级经理5星",
	                    					"children":null
	                    				},
	                    				{
	                    					"id":"75",
	                    					"text":"2-4-1 高级经理1钻",
	                    					"children":null
	                    				},
	                    				{
	                    					"id":"76",
	                    					"text":"2-4-2 高级经理2钻",
	                    					"children":null
	                    				},
	                    				{
	                    					"id":"77",
	                    					"text":"2-4-3 高级经理3钻",
	                    					"children":null
	                    				},
	                    				{
	                    					"id":"78",
	                    					"text":"2-4-4 高级经理4钻",
	                    					"children":null
	                    				},
	                    				{
	                    					"id":"79",
	                    					"text":"2-4-5 高级经理5钻",
	                    					"children":null
	                    				},
	                    				{
	                    					"id":"80",
	                    					"text":"2-5-1 高级经理1冠",
	                    					"children":null
	                    				},
	                    				{
	                    					"id":"81",
	                    					"text":"2-5-2 高级经理2冠",
	                    					"children":null
	                    				},
	                    				{
	                    					"id":"82",
	                    					"text":"2-5-3 高级经理3冠",
	                    					"children":null
	                    				},
	                    				{
	                    					"id":"83",
	                    					"text":"2-5-4 高级经理4冠",
	                    					"children":null
	                    				},
	                    				{
	                    					"id":"84",
	                    					"text":"2-5-5 高级经理5冠",
	                    					"children":null
	                    				}
	                    			]
	                    		},
	                    		{
	                    			"id":"d28",
		                    		"text":"2 客户经理/客户主管",
		                    		"children":[
		                    			{
		                    				"id":"113",
			                    			"text":"2-1 客户主管",
			                    			"children":null
			                    		},
			                    		{
			                    			"id":"97",
				                    		"text":"2-2 客户经理",
				                    		"children":null
				                    	},
			                    		{
			                    			"id":"98",
				                    		"text":"2-3-1 高级客户经理1星",
				                    		"children":null
				                    	},
			                    		{
			                    			"id":"99",
				                    		"text":"2-3-2 高级客户经理2星",
				                    		"children":null
				                    	},
			                    		{
			                    			"id":"100",
				                    		"text":"2-3-3 高级客户经理3星",
				                    		"children":null
				                    	},
			                    		{
			                    			"id":"101",
				                    		"text":"2-3-4 高级客户经理4星",
				                    		"children":null
				                    	},
			                    		{
			                    			"id":"102",
				                    		"text":"2-3-5 高级客户经理5星",
				                    		"children":null
				                    	},
			                    		{
			                    			"id":"103",
				                    		"text":"2-4-1 高级客户经理1钻",
				                    		"children":null
				                    	},
			                    		{
			                    			"id":"104",
				                    		"text":"2-4-2 高级客户经理2钻",
				                    		"children":null
				                    	},
			                    		{
			                    			"id":"105",
				                    		"text":"2-4-3 高级客户经理3钻",
				                    		"children":null
				                    	},
			                    		{
			                    			"id":"106",
				                    		"text":"2-4-4 高级客户经理4钻",
				                    		"children":null
				                    	},
			                    		{
			                    			"id":"107",
				                    		"text":"2-4-5 高级客户经理5钻",
				                    		"children":null
				                    	},
			                    		{
			                    			"id":"108",
				                    		"text":"2-5-1 高级客户经理1冠",
				                    		"children":null
				                    	},
			                    		{
			                    			"id":"109",
				                    		"text":"2-5-2 高级客户经理2冠",
				                    		"children":null
				                    	},
			                    		{
			                    			"id":"110",
				                    		"text":"2-5-3 高级客户经理3冠",
				                    		"children":null
				                    	},
			                    		{
			                    			"id":"111",
			                    			"text":"2-5-4 高级客户经理4冠",
			                    			"children":null
			                    		},
			                    		{
			                    			"id":"112",
			                    			"text":"2-5-5 高级客户经理5冠",
			                    			"children":null
			                    		}
			                    	]
			                    },
			                    {
			                    	"id":"d16",
				                    "text":"3 区域总监",
				                    "children":[
					                    {
					                    	"id":"38",
					                    	"text":"3-1 区域总监",
					                    	"children":null
					                    },
					                    {
					                    	"id":"39",
					                    	"text":"3-2 资深区域总监",
					                    	"children":null
					                    },
					                    {
					                    	"id":"40",
					                    	"text":"3-3 高级区域总监",
					                    	"children":null
					                    },
					                    {
					                    	"id":"41",
					                    	"text":"3-4 资深高级区域总监",
					                    	"children":null
					                    }
					                ]
					            },
					            {
					            	"id":"d15",
					            	"text":"4 营运副总经理",
					            	"children":[
					            		{
					            			"id":"34",
					            			"text":"4-1 营运副总经理",
					            			"children":null
					            		},
					            		{
					            			"id":"35",
					            			"text":"4-2 资深营运副总经理",
					            			"children":null
					            		},
					            		{
					            			"id":"36",
					            			"text":"4-3 高级营运副总经理",
					            			"children":null
					            		},
					            		{
					            			"id":"37",
					            			"text":"4-4 资深高级营运副总经理",
					            			"children":null
					            		}
					            	]
					            },
					            {
					            	"id":"d14",
					            	"text":"5 事业部总经理",
					            	"children":[
					            		{
					            			"id":"31",
					            			"text":"5-1 事业部总经理",
					            			"children":null
					            		},
					            		{
					            			"id":"32",
					            			"text":"5-2 资深事业部总经理",
					            			"children":null
					            		},
					            		{
					            			"id":"33",
					            			"text":"5-3 高级事业部总经理",
					            			"children":null
					            		}
					            	]
					            },
					            {
					            	"id":"d12",
					            	"text":"6 大区总裁/集团总监",
					            	"children":[
					            		{
					            			"id":"29",
					            			"text":"6-1 大区总裁",
					            			"children":null
					            		},
					            		{
					            			"id":"30",
					            			"text":"6-1 集团总监",
					            			"children":null
					            		}
					            	]
					            },
					            {
					            	"id":"d11",
					            	"text":"7 集团总裁",
					            	"children":[
					            		{
					            			"id":"28",
					            			"text":"7-1 集团总裁",
					            			"children":null
					            		}
					            	]
					            }
					        ]
					    }
					],
					"paymentBaseList" : [//缴费基数
						{
							"id" : 1,
							"paymentBase" : 3022,
							"status" : 1 //现在
						},
						{
							"id" : 2,
							"paymentBase" : 2047,
							"status" : 1
						},
						{
							"id" : 3,
							"paymentBase" : 1800,
							"status" : 0 //历史
						},
						{
							"id" : 4,
							"paymentBase" : 1649,
							"status" : 0
						},
						{
							"id" : 5,
							"paymentBase" : 1490,
							"status" : 0
						}
					],
					"applyStatusList" : [//申请状态
						{
							"id" : 0,
							"name" : "失败"
						},
						{
							"id" : 1,
							"name" : "成功"
						}
					]
				}
			});
		});

	/** 模块：社保管理 **/
		/** 页面：缴纳信息（一览） **/
			//(1)搜索条件选项请求（公共接口：获取缴纳地、社保卡、异地申请表、职等职级、户籍性质）

			//(2)查询，获得结果
			app.post('/si/base/list', function(req, res){
				res.json({
	message: "",
	status: "ok",
	data: {
		totalCount: 3006,
		pageNo: 1,
		pageSize: 30,
		resultList: [
			{
				creator: 89695,
				createTime: 1403181856367,
				updator: 90378,
				updateTime: 1403678398430,
				id: 3024,
				userCode: 84006,
				userName: "潘光",
				orgId: 20988,
				orgName: "碧云A组",
				newJoinDate: 1293465600000,
				serialName: "营销业务职系",
				titleLevelDegree: "1-2",
				levelId: 48,
				credentialsNo: "342222198602282830",
				censusId: 5,
				censusName: "无",
				paymentStatus: 1,
				paymentLocationId: 1,
				paymentLocationName: "上海",
				paymentTypeId: 1,
				paymentTypeName: "员工自缴",
				paymentBase: 3022,
				beginDate: 1403107200000,
				endDate: null,
				possessSocialCard: null,
				socialCardActivateDate: null,
				applyForm: 1,
				applyFormDate: null,
				extraBeginDate: null,
				extraEndDate: null,
				comment: "1",
				applyFormAttachUrl: "/attach/si/201406/140625135803284.jpg",
				attachUrl: "/attach/si/201406/140625135814839.jpg",
				status: 1,
				sort: 1
			},
			{
				creator: 89695,
				createTime: 1403181856367,
				updator: 90378,
				updateTime: 1403840838450,
				id: 3025,
				userCode: 86954,
				userName: "李祖松",
				orgId: 20709,
				orgName: "幸福A组",
				newJoinDate: 1394035200000,
				serialName: "营销业务职系",
				titleLevelDegree: "1-3",
				levelId: 49,
				credentialsNo: "342425198407204212",
				censusId: 5,
				censusName: "无",
				paymentStatus: 1,
				paymentLocationId: 1,
				paymentLocationName: "上海",
				paymentTypeId: 1,
				paymentTypeName: "员工自缴",
				paymentBase: 3022,
				beginDate: 1403107200000,
				endDate: null,
				possessSocialCard: null,
				socialCardActivateDate: null,
				applyForm: 1,
				applyFormDate: null,
				extraBeginDate: null,
				extraEndDate: null,
				comment: null,
				applyFormAttachUrl: "/attach/si/201406/140627114743491.jpg",
				attachUrl: "/attach/si/201406/140627114749270.jpg",
				status: 1,
				sort: 1
			},
			{
				creator: 89695,
				createTime: 1403181856367,
				updator: 90378,
				updateTime: 1403839928773,
				id: 3026,
				userCode: 105212,
				userName: "方善周",
				orgId: 21622,
				orgName: "新龙A组",
				newJoinDate: 1400083200000,
				serialName: "营销业务职系",
				titleLevelDegree: "2-1",
				levelId: 113,
				credentialsNo: "362321198910236513",
				censusId: 0,
				censusName: "外地农村",
				paymentStatus: 1,
				paymentLocationId: 1,
				paymentLocationName: "上海",
				paymentTypeId: 1,
				paymentTypeName: "员工自缴",
				paymentBase: 3022,
				beginDate: 1403107200000,
				endDate: null,
				possessSocialCard: null,
				socialCardActivateDate: null,
				applyForm: 1,
				applyFormDate: null,
				extraBeginDate: null,
				extraEndDate: null,
				comment: "我是封其源，我就是厉害。",
				applyFormAttachUrl: "/attach/si/201406/140627095137635.jpg",
				attachUrl: "/attach/si/201406/140627095141913.jpg",
				status: 1,
				sort: 1
			},
			{
				creator: 89695,
				createTime: 1403179571900,
				updator: 80001,
				updateTime: 1403687530697,
				id: 3027,
				userCode: 81398,
				userName: "韩彬",
				orgId: 21083,
				orgName: "代理项目六部",
				newJoinDate: 1251993600000,
				serialName: "营销职系（代理部）",
				titleLevelDegree: "1-1",
				levelId: 85,
				credentialsNo: "110101197711011515",
				censusId: 1,
				censusName: "外地城镇",
				paymentStatus: 1,
				paymentLocationId: 3,
				paymentLocationName: "宁波",
				paymentTypeId: 0,
				paymentTypeName: "公司代缴",
				paymentBase: 3022,
				beginDate: 1403107200000,
				endDate: null,
				possessSocialCard: 1,
				socialCardActivateDate: null,
				applyForm: null,
				applyFormDate: null,
				extraBeginDate: null,
				extraEndDate: null,
				comment: "7",
				applyFormAttachUrl: "/attach/si/201406/140625142754788.jpg",
				attachUrl: "/attach/si/201406/140625140848631.jpg",
				status: 1,
				sort: 1
			},
			{
				creator: 89695,
				createTime: 1403179571900,
				updator: 80001,
				updateTime: 1403688133670,
				id: 3028,
				userCode: 81905,
				userName: "张荣",
				orgId: 20619,
				orgName: "徐汇总店E组",
				newJoinDate: 1363708800000,
				serialName: "营销业务职系",
				titleLevelDegree: "1-2",
				levelId: 48,
				credentialsNo: "321321198910142922",
				censusId: 5,
				censusName: "无",
				paymentStatus: 1,
				paymentLocationId: 3,
				paymentLocationName: "宁波",
				paymentTypeId: 0,
				paymentTypeName: "公司代缴",
				paymentBase: 1470,
				beginDate: 1403107200000,
				endDate: null,
				possessSocialCard: 1,
				socialCardActivateDate: null,
				applyForm: null,
				applyFormDate: null,
				extraBeginDate: null,
				extraEndDate: null,
				comment: "test",
				applyFormAttachUrl: "/attach/si/201406/140625141226194.jpg",
				attachUrl: "/attach/si/201406/140625141228662.jpg",
				status: 1,
				sort: 1
			},
			{
				creator: 89695,
				createTime: 1403179571900,
				updator: 80001,
				updateTime: 1403688134680,
				id: 3029,
				userCode: 94057,
				userName: "赵领春",
				orgId: 20828,
				orgName: "浦江D组",
				newJoinDate: 1403107200000,
				serialName: "营销业务职系",
				titleLevelDegree: "1-4",
				levelId: 50,
				credentialsNo: "152222197704251023",
				censusId: 1,
				censusName: "外地城镇",
				paymentStatus: 1,
				paymentLocationId: 3,
				paymentLocationName: "宁波",
				paymentTypeId: 0,
				paymentTypeName: "公司代缴",
				paymentBase: 1470,
				beginDate: 1403107200000,
				endDate: null,
				possessSocialCard: 1,
				socialCardActivateDate: null,
				applyForm: null,
				applyFormDate: null,
				extraBeginDate: null,
				extraEndDate: null,
				comment: "1111",
				applyFormAttachUrl: "/attach/si/201406/140625141249826.jpg",
				attachUrl: null,
				status: 1,
				sort: 1
			},
			{
				creator: 89695,
				createTime: 1403179571900,
				updator: 90378,
				updateTime: 1403677078440,
				id: 3030,
				userCode: 88351,
				userName: "何胜",
				orgId: 20567,
				orgName: "古北御翠A组",
				newJoinDate: 1401552000000,
				serialName: "营销业务职系",
				titleLevelDegree: "2-2",
				levelId: 97,
				credentialsNo: "654127198010200011",
				censusId: 1,
				censusName: "外地城镇",
				paymentStatus: 1,
				paymentLocationId: 3,
				paymentLocationName: "宁波",
				paymentTypeId: 0,
				paymentTypeName: "公司代缴",
				paymentBase: 1470,
				beginDate: 1403107200000,
				endDate: null,
				possessSocialCard: null,
				socialCardActivateDate: null,
				applyForm: null,
				applyFormDate: null,
				extraBeginDate: null,
				extraEndDate: null,
				comment: "123",
				applyFormAttachUrl: "/attach/si/201406/140625141823450.jpg",
				attachUrl: "/attach/si/201406/140625141825982.jpg",
				status: 1,
				sort: 1
			},
			{
				creator: 89695,
				createTime: 1403173269003,
				updator: 80001,
				updateTime: 1403761637807,
				id: 3023,
				userCode: 92449,
				userName: "孙新国",
				orgId: 20701,
				orgName: "豪宅二部A组",
				newJoinDate: 1402934400000,
				serialName: "营销业务职系",
				titleLevelDegree: "1-2",
				levelId: 48,
				credentialsNo: "372925198810292112",
				censusId: 5,
				censusName: "无",
				paymentStatus: 1,
				paymentLocationId: 3,
				paymentLocationName: "宁波",
				paymentTypeId: 0,
				paymentTypeName: "公司代缴",
				paymentBase: 1470,
				beginDate: 1403107200000,
				endDate: null,
				possessSocialCard: 1,
				socialCardActivateDate: null,
				applyForm: null,
				applyFormDate: null,
				extraBeginDate: null,
				extraEndDate: null,
				comment: "456",
				applyFormAttachUrl: "/attach/si/201406/140625141841554.jpg",
				attachUrl: null,
				status: 1,
				sort: 1
			},
			{
				creator: 89695,
				createTime: 1403168196377,
				updator: 90378,
				updateTime: 1403677108227,
				id: 3012,
				userCode: 103796,
				userName: "赵中华",
				orgId: 20417,
				orgName: "高泾B组",
				newJoinDate: 1396108800000,
				serialName: "行政管理职系",
				titleLevelDegree: "4-3",
				levelId: 12,
				credentialsNo: "320705198309102058",
				censusId: null,
				censusName: null,
				paymentStatus: 1,
				paymentLocationId: 1,
				paymentLocationName: "上海",
				paymentTypeId: 0,
				paymentTypeName: "公司代缴",
				paymentBase: 10100,
				beginDate: 1403107200000,
				endDate: null,
				possessSocialCard: null,
				socialCardActivateDate: null,
				applyForm: null,
				applyFormDate: null,
				extraBeginDate: null,
				extraEndDate: null,
				comment: "789",
				applyFormAttachUrl: null,
				attachUrl: "/attach/si/201406/140625141852561.jpg",
				status: 1,
				sort: 1
			},
			{
				creator: 89695,
				createTime: 1403168196377,
				updator: 90378,
				updateTime: 1403846509543,
				id: 3013,
				userCode: 104394,
				userName: "潘为虎",
				orgId: 20346,
				orgName: "南滨江A组",
				newJoinDate: 1397491200000,
				serialName: "行政管理职系",
				titleLevelDegree: "3-2",
				levelId: 15,
				credentialsNo: "342327197503184015",
				censusId: null,
				censusName: null,
				paymentStatus: 1,
				paymentLocationId: 1,
				paymentLocationName: "上海",
				paymentTypeId: 0,
				paymentTypeName: "公司代缴",
				paymentBase: 5050,
				beginDate: 1403107200000,
				endDate: null,
				possessSocialCard: null,
				socialCardActivateDate: null,
				applyForm: 1,
				applyFormDate: null,
				extraBeginDate: null,
				extraEndDate: null,
				comment: "啊",
				applyFormAttachUrl: "/attach/si/201406/140627132156007.jpg",
				attachUrl: "/attach/si/201406/140627132223488.jpg",
				status: 1,
				sort: 1
			},
			{
				creator: 89695,
				createTime: 1403168196377,
				updator: 89695,
				updateTime: 1403168196377,
				id: 3014,
				userCode: 80451,
				userName: "王赛月",
				orgId: 20082,
				orgName: "达安B组",
				newJoinDate: 1194364800000,
				serialName: "营销管理职系",
				titleLevelDegree: "2-3-3",
				levelId: 72,
				credentialsNo: "321087198101163422",
				censusId: 1,
				censusName: "外地城镇",
				paymentStatus: 1,
				paymentLocationId: 1,
				paymentLocationName: "上海",
				paymentTypeId: 0,
				paymentTypeName: "公司代缴",
				paymentBase: 4050,
				beginDate: 1403107200000,
				endDate: null,
				possessSocialCard: null,
				socialCardActivateDate: null,
				applyForm: null,
				applyFormDate: null,
				extraBeginDate: null,
				extraEndDate: null,
				comment: null,
				applyFormAttachUrl: null,
				attachUrl: null,
				status: 1,
				sort: 1
			},
			{
				creator: 89695,
				createTime: 1403168196377,
				updator: 90378,
				updateTime: 1403677202083,
				id: 3015,
				userCode: 105229,
				userName: "叶馨文",
				orgId: 21456,
				orgName: "执秘西十二区",
				newJoinDate: 1400169600000,
				serialName: "行政管理职系",
				titleLevelDegree: "5-4",
				levelId: 116,
				credentialsNo: "310104199201026026",
				censusId: null,
				censusName: null,
				paymentStatus: 1,
				paymentLocationId: 1,
				paymentLocationName: "上海",
				paymentTypeId: 0,
				paymentTypeName: "公司代缴",
				paymentBase: 15108,
				beginDate: 1403107200000,
				endDate: null,
				possessSocialCard: null,
				socialCardActivateDate: null,
				applyForm: null,
				applyFormDate: null,
				extraBeginDate: null,
				extraEndDate: null,
				comment: "哦",
				applyFormAttachUrl: "/attach/si/201406/140625142028090.jpg",
				attachUrl: null,
				status: 1,
				sort: 1
			},
			{
				creator: 89695,
				createTime: 1403168196377,
				updator: 90378,
				updateTime: 1403677212477,
				id: 3016,
				userCode: 103293,
				userName: "谷西山",
				orgId: 21384,
				orgName: "大宁D组",
				newJoinDate: 1395331200000,
				serialName: "营销管理职系",
				titleLevelDegree: "4-3",
				levelId: 36,
				credentialsNo: "320722199310247714",
				censusId: 0,
				censusName: "外地农村",
				paymentStatus: 1,
				paymentLocationId: 1,
				paymentLocationName: "上海",
				paymentTypeId: 0,
				paymentTypeName: "公司代缴",
				paymentBase: 15108,
				beginDate: 1403107200000,
				endDate: null,
				possessSocialCard: null,
				socialCardActivateDate: null,
				applyForm: null,
				applyFormDate: null,
				extraBeginDate: null,
				extraEndDate: null,
				comment: "哈",
				applyFormAttachUrl: null,
				attachUrl: "/attach/si/201406/140625142038620.jpg",
				status: 1,
				sort: 1
			},
			{
				creator: 89695,
				createTime: 1403168196377,
				updator: 89695,
				updateTime: 1403168196377,
				id: 3017,
				userCode: 80236,
				userName: "许开田",
				orgId: 21354,
				orgName: "海丽D组",
				newJoinDate: 1383494400000,
				serialName: "营销管理职系",
				titleLevelDegree: "2-2",
				levelId: 42,
				credentialsNo: "532124198412161336",
				censusId: 1,
				censusName: "外地城镇",
				paymentStatus: 1,
				paymentLocationId: 1,
				paymentLocationName: "上海",
				paymentTypeId: 0,
				paymentTypeName: "公司代缴",
				paymentBase: 4050,
				beginDate: 1403107200000,
				endDate: null,
				possessSocialCard: null,
				socialCardActivateDate: null,
				applyForm: null,
				applyFormDate: null,
				extraBeginDate: null,
				extraEndDate: null,
				comment: null,
				applyFormAttachUrl: null,
				attachUrl: null,
				status: 1,
				sort: 1
			},
			{
				creator: 89695,
				createTime: 1403168196377,
				updator: 89695,
				updateTime: 1403772440947,
				id: 3018,
				userCode: 105013,
				userName: "陆志刚",
				orgId: 21481,
				orgName: "莲花店B组",
				newJoinDate: 1399478400000,
				serialName: "营销管理职系",
				titleLevelDegree: "2-2",
				levelId: 42,
				credentialsNo: "340103197502154531",
				censusId: null,
				censusName: null,
				paymentStatus: 1,
				paymentLocationId: 1,
				paymentLocationName: "上海",
				paymentTypeId: 0,
				paymentTypeName: "公司代缴",
				paymentBase: 4050,
				beginDate: 1403107200000,
				endDate: null,
				possessSocialCard: null,
				socialCardActivateDate: null,
				applyForm: 1,
				applyFormDate: null,
				extraBeginDate: null,
				extraEndDate: null,
				comment: "dagfdsgfa",
				applyFormAttachUrl: "/attach/si/201406/140626164725605.bmp",
				attachUrl: "/attach/si/201406/140626164701612.gif",
				status: 1,
				sort: 1
			},
			{
				creator: 89695,
				createTime: 1403168196377,
				updator: 90378,
				updateTime: 1403677261043,
				id: 3019,
				userCode: 81456,
				userName: "薄晓玲",
				orgId: 20755,
				orgName: "静安A组",
				newJoinDate: 1255449600000,
				serialName: "营销业务职系",
				titleLevelDegree: "1-1",
				levelId: 47,
				credentialsNo: "310113198207075322",
				censusId: 3,
				censusName: "上海城镇",
				paymentStatus: 1,
				paymentLocationId: 1,
				paymentLocationName: "上海",
				paymentTypeId: 0,
				paymentTypeName: "公司代缴",
				paymentBase: 3022,
				beginDate: 1403107200000,
				endDate: null,
				possessSocialCard: null,
				socialCardActivateDate: null,
				applyForm: null,
				applyFormDate: null,
				extraBeginDate: null,
				extraEndDate: null,
				comment: "1",
				applyFormAttachUrl: "/attach/si/201406/140625142123933.jpg",
				attachUrl: "/attach/si/201406/140625142126463.jpg",
				status: 1,
				sort: 1
			},
			{
				creator: 89695,
				createTime: 1403168196377,
				updator: 90378,
				updateTime: 1404107781160,
				id: 3020,
				userCode: 104064,
				userName: "尚林春",
				orgId: 20828,
				orgName: "浦江D组",
				newJoinDate: 1396540800000,
				serialName: "营销管理职系",
				titleLevelDegree: "3-3",
				levelId: 40,
				credentialsNo: "340321197603026233",
				censusId: 1,
				censusName: "外地城镇",
				paymentStatus: 1,
				paymentLocationId: 1,
				paymentLocationName: "上海",
				paymentTypeId: 0,
				paymentTypeName: "公司代缴",
				paymentBase: 10100,
				beginDate: 1403107200000,
				endDate: null,
				possessSocialCard: null,
				socialCardActivateDate: null,
				applyForm: 1,
				applyFormDate: null,
				extraBeginDate: null,
				extraEndDate: null,
				comment: "你好。",
				applyFormAttachUrl: "/attach/si/201406/140630135450151.jpg",
				attachUrl: "/attach/si/201406/140630135454467.jpg",
				status: 1,
				sort: 1
			},
			{
				creator: 89695,
				createTime: 1403168196377,
				updator: 89695,
				updateTime: 1403168196377,
				id: 3021,
				userCode: 104756,
				userName: "黄晓雨",
				orgId: 21358,
				orgName: "宝华D组",
				newJoinDate: 1398614400000,
				serialName: "行政技术职系",
				titleLevelDegree: "3-3",
				levelId: 58,
				credentialsNo: "320211198101134114",
				censusId: 1,
				censusName: "外地城镇",
				paymentStatus: 1,
				paymentLocationId: 1,
				paymentLocationName: "上海",
				paymentTypeId: 0,
				paymentTypeName: "公司代缴",
				paymentBase: 3022,
				beginDate: 1403107200000,
				endDate: null,
				possessSocialCard: null,
				socialCardActivateDate: null,
				applyForm: null,
				applyFormDate: null,
				extraBeginDate: null,
				extraEndDate: null,
				comment: null,
				applyFormAttachUrl: null,
				attachUrl: null,
				status: 1,
				sort: 1
			},
			{
				creator: 89695,
				createTime: 1403168196377,
				updator: 89695,
				updateTime: 1403168196377,
				id: 3022,
				userCode: 80218,
				userName: "高胜全",
				orgId: 20371,
				orgName: "代理项目二部",
				newJoinDate: 1167408000000,
				serialName: "营销职系（代理部）",
				titleLevelDegree: "2-5",
				levelId: 95,
				credentialsNo: "342529198302283218",
				censusId: 0,
				censusName: "外地农村",
				paymentStatus: 1,
				paymentLocationId: 1,
				paymentLocationName: "上海",
				paymentTypeId: 0,
				paymentTypeName: "公司代缴",
				paymentBase: 5050,
				beginDate: 1403107200000,
				endDate: null,
				possessSocialCard: null,
				socialCardActivateDate: null,
				applyForm: null,
				applyFormDate: null,
				extraBeginDate: null,
				extraEndDate: null,
				comment: null,
				applyFormAttachUrl: null,
				attachUrl: null,
				status: 1,
				sort: 1
			},
			{
				creator: 89695,
				createTime: 1403158586623,
				updator: 89695,
				updateTime: 1403158586623,
				id: 2987,
				userCode: 104404,
				userName: "齐项男",
				orgId: 20376,
				orgName: "广告设计部",
				newJoinDate: 1400428800000,
				serialName: "行政技术职系",
				titleLevelDegree: "5",
				levelId: 68,
				credentialsNo: "210302199009300915",
				censusId: null,
				censusName: null,
				paymentStatus: 1,
				paymentLocationId: 1,
				paymentLocationName: "上海",
				paymentTypeId: 0,
				paymentTypeName: "公司代缴",
				paymentBase: 3022,
				beginDate: 1403107200000,
				endDate: null,
				possessSocialCard: null,
				socialCardActivateDate: null,
				applyForm: null,
				applyFormDate: null,
				extraBeginDate: null,
				extraEndDate: null,
				comment: null,
				applyFormAttachUrl: null,
				attachUrl: null,
				status: 1,
				sort: 1
			},
			{
				creator: 89695,
				createTime: 1403158586623,
				updator: 89695,
				updateTime: 1403158586623,
				id: 2980,
				userCode: 92673,
				userName: "周辰康",
				orgId: 20711,
				orgName: "幸福C组",
				newJoinDate: 1400428800000,
				serialName: "营销业务职系",
				titleLevelDegree: "3",
				levelId: 48,
				credentialsNo: "310106198812204096",
				censusId: null,
				censusName: null,
				paymentStatus: 1,
				paymentLocationId: 1,
				paymentLocationName: "上海",
				paymentTypeId: 0,
				paymentTypeName: "公司代缴",
				paymentBase: 3022,
				beginDate: 1403107200000,
				endDate: null,
				possessSocialCard: null,
				socialCardActivateDate: null,
				applyForm: null,
				applyFormDate: null,
				extraBeginDate: null,
				extraEndDate: null,
				comment: null,
				applyFormAttachUrl: null,
				attachUrl: null,
				status: 1,
				sort: 1
			},
			{
				creator: 89695,
				createTime: 1403158586623,
				updator: 89695,
				updateTime: 1403158586623,
				id: 2981,
				userCode: 105224,
				userName: "段旋旋",
				orgId: 20475,
				orgName: "执秘西二区",
				newJoinDate: 1400169600000,
				serialName: "行政技术职系",
				titleLevelDegree: "3",
				levelId: 66,
				credentialsNo: "342221198905091525",
				censusId: null,
				censusName: null,
				paymentStatus: 1,
				paymentLocationId: 1,
				paymentLocationName: "上海",
				paymentTypeId: 0,
				paymentTypeName: "公司代缴",
				paymentBase: 3022,
				beginDate: 1403107200000,
				endDate: null,
				possessSocialCard: null,
				socialCardActivateDate: null,
				applyForm: null,
				applyFormDate: null,
				extraBeginDate: null,
				extraEndDate: null,
				comment: null,
				applyFormAttachUrl: null,
				attachUrl: null,
				status: 1,
				sort: 1
			},
			{
				creator: 89695,
				createTime: 1403158586623,
				updator: 89695,
				updateTime: 1404096579873,
				id: 2982,
				userCode: 96530,
				userName: "蒋贻凯",
				orgId: 20989,
				orgName: "莘松A组",
				newJoinDate: 1398873600000,
				serialName: "营销业务职系",
				titleLevelDegree: "3",
				levelId: 48,
				credentialsNo: "310101198301018951",
				censusId: null,
				censusName: null,
				paymentStatus: 1,
				paymentLocationId: 1,
				paymentLocationName: "上海",
				paymentTypeId: 0,
				paymentTypeName: "公司代缴",
				paymentBase: 3022,
				beginDate: 1403107200000,
				endDate: null,
				possessSocialCard: null,
				socialCardActivateDate: null,
				applyForm: 0,
				applyFormDate: null,
				extraBeginDate: null,
				extraEndDate: null,
				comment: "sdafasfdsa fsdafsdaf",
				applyFormAttachUrl: null,
				attachUrl: null,
				status: 1,
				sort: 1
			},
			{
				creator: 89695,
				createTime: 1403158586623,
				updator: 89695,
				updateTime: 1404096079830,
				id: 2983,
				userCode: 82089,
				userName: "施雅瑾",
				orgId: 21618,
				orgName: "中华C组",
				newJoinDate: 1266940800000,
				serialName: "营销业务职系",
				titleLevelDegree: "2",
				levelId: 47,
				credentialsNo: "310107198603153444",
				censusId: 3,
				censusName: "上海城镇",
				paymentStatus: 1,
				paymentLocationId: 1,
				paymentLocationName: "上海",
				paymentTypeId: 0,
				paymentTypeName: "公司代缴",
				paymentBase: 3022,
				beginDate: 1403107200000,
				endDate: null,
				possessSocialCard: null,
				socialCardActivateDate: null,
				applyForm: 1,
				applyFormDate: null,
				extraBeginDate: null,
				extraEndDate: null,
				comment: "软件测试软件测试软件测试软件测试软件测试软件测试软件测试软件测试软件测试软件测试软件测试软件测试软件测试软件测试软件测试软件测试软件测试软件测试软件测试软件测试软件测试软件测试软件测试软件测试软件测试",
				applyFormAttachUrl: "/attach/si/201406/140630104111619.jpg",
				attachUrl: "/attach/si/201406/140630104117660.png",
				status: 1,
				sort: 1
			},
			{
				creator: 89695,
				createTime: 1403158586623,
				updator: 89695,
				updateTime: 1403158586623,
				id: 2984,
				userCode: 80848,
				userName: "陈丽岚",
				orgId: 20069,
				orgName: "交按一部（静安、闸北）",
				newJoinDate: 1345132800000,
				serialName: "行政管理职系",
				titleLevelDegree: "4",
				levelId: 19,
				credentialsNo: "430702198605035220",
				censusId: 1,
				censusName: "外地城镇",
				paymentStatus: 1,
				paymentLocationId: 1,
				paymentLocationName: "上海",
				paymentTypeId: 0,
				paymentTypeName: "公司代缴",
				paymentBase: 3022,
				beginDate: 1403107200000,
				endDate: null,
				possessSocialCard: null,
				socialCardActivateDate: null,
				applyForm: null,
				applyFormDate: null,
				extraBeginDate: null,
				extraEndDate: null,
				comment: null,
				applyFormAttachUrl: null,
				attachUrl: null,
				status: 1,
				sort: 1
			},
			{
				creator: 89695,
				createTime: 1403158586623,
				updator: 90378,
				updateTime: 1403849846070,
				id: 2985,
				userCode: 82087,
				userName: "王峥",
				orgId: 20755,
				orgName: "静安A组",
				newJoinDate: 1266940800000,
				serialName: "营销业务职系",
				titleLevelDegree: "2",
				levelId: 47,
				credentialsNo: "310104198706080023",
				censusId: 3,
				censusName: "上海城镇",
				paymentStatus: 1,
				paymentLocationId: 1,
				paymentLocationName: "上海",
				paymentTypeId: 0,
				paymentTypeName: "公司代缴",
				paymentBase: 3022,
				beginDate: 1403107200000,
				endDate: null,
				possessSocialCard: null,
				socialCardActivateDate: null,
				applyForm: 1,
				applyFormDate: null,
				extraBeginDate: null,
				extraEndDate: null,
				comment: null,
				applyFormAttachUrl: "/attach/si/201406/140627141725278.jpg",
				attachUrl: "/attach/si/201406/140627141729753.jpg",
				status: 1,
				sort: 1
			},
			{
				creator: 89695,
				createTime: 1403158586623,
				updator: 89695,
				updateTime: 1403158586623,
				id: 2986,
				userCode: 105281,
				userName: "爱新觉罗",
				orgId: 28,
				orgName: "建国店",
				newJoinDate: 1401552000000,
				serialName: "行政管理职系",
				titleLevelDegree: "3",
				levelId: 18,
				credentialsNo: "110101198301013256",
				censusId: null,
				censusName: null,
				paymentStatus: 1,
				paymentLocationId: 1,
				paymentLocationName: "上海",
				paymentTypeId: 0,
				paymentTypeName: "公司代缴",
				paymentBase: 3022,
				beginDate: 1403107200000,
				endDate: null,
				possessSocialCard: null,
				socialCardActivateDate: null,
				applyForm: null,
				applyFormDate: null,
				extraBeginDate: null,
				extraEndDate: null,
				comment: null,
				applyFormAttachUrl: null,
				attachUrl: null,
				status: 1,
				sort: 1
			},
			{
				creator: 90378,
				createTime: 1403154982570,
				updator: 90378,
				updateTime: 1403154982570,
				id: 2974,
				userCode: 92590,
				userName: "周奇磊",
				orgId: 20712,
				orgName: "莘松二店A组",
				newJoinDate: 1402848000000,
				serialName: "营销业务职系",
				titleLevelDegree: "3",
				levelId: 48,
				credentialsNo: "411424198904240557",
				censusId: 5,
				censusName: "无",
				paymentStatus: 1,
				paymentLocationId: 3,
				paymentLocationName: "宁波",
				paymentTypeId: 0,
				paymentTypeName: "公司代缴",
				paymentBase: 1470,
				beginDate: 1403107200000,
				endDate: null,
				possessSocialCard: null,
				socialCardActivateDate: null,
				applyForm: null,
				applyFormDate: null,
				extraBeginDate: null,
				extraEndDate: null,
				comment: null,
				applyFormAttachUrl: null,
				attachUrl: null,
				status: 1,
				sort: 1
			},
			{
				creator: 90378,
				createTime: 1403154982570,
				updator: 89695,
				updateTime: 1403173931510,
				id: 2975,
				userCode: 92449,
				userName: "孙新国",
				orgId: 20701,
				orgName: "豪宅二部A组",
				newJoinDate: 1402934400000,
				serialName: "营销业务职系",
				titleLevelDegree: "3",
				levelId: 48,
				credentialsNo: "372925198810292112",
				censusId: 5,
				censusName: "无",
				paymentStatus: 0,
				paymentLocationId: 3,
				paymentLocationName: "宁波",
				paymentTypeId: 0,
				paymentTypeName: "公司代缴",
				paymentBase: 1470,
				beginDate: 1403107200000,
				endDate: 1403107200000,
				possessSocialCard: null,
				socialCardActivateDate: null,
				applyForm: null,
				applyFormDate: null,
				extraBeginDate: null,
				extraEndDate: null,
				comment: null,
				applyFormAttachUrl: null,
				attachUrl: null,
				status: 1,
				sort: 1
			},
			{
				creator: 90378,
				createTime: 1403154982570,
				updator: 90378,
				updateTime: 1403154982570,
				id: 2976,
				userCode: 80982,
				userName: "沈露",
				orgId: 21218,
				orgName: "威海B组",
				newJoinDate: 1234454400000,
				serialName: "营销业务职系",
				titleLevelDegree: "2",
				levelId: 47,
				credentialsNo: "320522198510063922",
				censusId: 1,
				censusName: "外地城镇",
				paymentStatus: 1,
				paymentLocationId: 3,
				paymentLocationName: "宁波",
				paymentTypeId: 0,
				paymentTypeName: "公司代缴",
				paymentBase: 1470,
				beginDate: 1403107200000,
				endDate: null,
				possessSocialCard: null,
				socialCardActivateDate: null,
				applyForm: null,
				applyFormDate: null,
				extraBeginDate: null,
				extraEndDate: null,
				comment: null,
				applyFormAttachUrl: null,
				attachUrl: null,
				status: 1,
				sort: 1
			}
		]
	}
				});
			});
			/*  异动申请表、附件的图片url，后台返回一个原始url，前端来拼装大图、缩略图url
			    原始url: fileName.png，fileName.jpeg（任意图片类型）
			    前端生成缩略图url规则：fileName.png_small.png，fileName.jpeg_small.jpeg
			    前端生成大图的url规则：fileName.png_big.png，fileName.jpeg_big.jpeg
			*/

			//(3)编辑-提交
			app.post('/si/base/submit', function(req, res){
				res.json({
					"status" : "ok",
					"message" : ""
				})
			});

			//(4)编辑 - 删除附件
			app.post('/si/base/attachDel', function(req, res){
				res.json({
					"status" : "ok",
					"message" : ""
				})
			});

			//(5)导出数据
			app.post('/si/base/export', function(req, res){
				res.json({
					"status" : "ok",
					"message" : ""
				})
			});

		/** 页面：上海新进 **/
			//(1)切换缴纳年月，联动缴纳批次、确认退出状态
			app.get('/si/shnew/switchdate', function(req, res){
				res.json({
					"status" : "ok",
					"message" : "",
					"data" : {
						"batchInfo" : {//缴纳批次
							"confirmStatus" : 0,//是否已确认缴纳 0-未确认，1-已确认
							"batchId" : "20140407-152351" //批次ID
						}
					}
				});
			});

			//(2)查询，获得结果
			app.get('/si/shnew/list', function(req, res){
				res.json({
					"status" : "ok",
					"message" : "",
					"data" : {
						"totalCount" : 120,   //总记录条数
						"pageNo" : 2,         //当前页号
						"pageSize" : 20, //每页记录数
						"resultList" : [
							{
								"userCode" : 88888, //工号
								"userName" : "张三", //姓名
								"orgId" : 99999, //部门
								"orgName" : "静安A组", //部门
								"newJoinDate" : 1302557950677, //入职时间
								"extraBeginDate" : 1302557950677, //补缴周期-起
								"extraEndDate" : 1402557950677, //补缴周期-止
								"credentialsNo" : "310023198912029981", //身份证号码
								"censusName" : "上海城镇", //户籍性质
								"censusId" : 3, //户籍性质Id
								"serialName" : "行政职系", //职系
								"titleLevelDegree" : "1-4", //职等职级 edit by Alex 20140614 titlelevelDegree -> titleLevelDegree
								"paymentBase" : 3022, //缴费基数
								"applyStatus" : 0, //是否失败 0-失败，1-成功
								"failureReason" : "缺少某某资料", //失败原因
								"comment" : "需要在XX时候提醒提交资料", //备注
								"paymentTypeId" : null, //是否自缴  1-员工自缴，还有其他状态 edit by Alex 20140614 paymentType -> paymentTypeId
								"id" : 11  //记录ID
							},
							{
								"userCode" : 88888, //工号
								"userName" : "李四", //姓名
								"orgId" : 99999, //部门
								"orgName" : "静安A组", //部门
								"newJoinDate" : 1302557950677, //入职时间
								"extraBeginDate" : 1302557950677, //补缴周期-起
								"extraEndDate" : 1402557950677, //补缴周期-止
								"credentialsNo" : "310023198912029981", //身份证号码
								"censusName" : "上海农村", //户籍性质
								"censusId" : 4, //户籍性质Id
								"serialName" : "行政职系", //职系
								"titleLevelDegree" : "2-1", //职等职级 edit by Alex 20140614 titlelevelDegree -> titleLevelDegree
								"paymentBase" : 3022, //缴费基数
								"applyStatus" : 1, //是否失败 0-失败，1-成功
								"failureReason" : "", //失败原因
								"comment" : "", //备注
								"paymentTypeId" : 1, //是否自缴  1-员工自缴，还有其他状态 edit by Alex 20140614 paymentType -> paymentTypeId
								"id" : 22  //记录ID
							}
						]
					}
				});
			});

			//(3)确认缴纳
			app.post('/si/shnew/confirmpayment', function(req, res){
				res.json({
					"status" : "ok",
					"message" : ""
				});
			});

			//列表操作 - 编辑
			app.post('/si/shnew/submit', function(req, res){
				res.json({
					"status" : "ok",
					"message" : ""
				});
			});


		/** 页面：上海退出 **/
			//(1)切换退出年月，联动退出批次、确认退出状态
			app.get('/si/shquit/switchdate', function(req, res){
				res.json({
					"status" : "ok",
					"message" : "",
					"data" : {
						"batchInfo" : [//退出批次下拉框选项
							{
								"confirmStatus" : 0,//是否已确认缴纳 0-未确认，1-已确认
								"batchId" : "20140407-152351" //批次ID
							},
							{
								"confirmStatus" : 0,//是否已确认缴纳 0-未确认，1-已确认
								"batchId" : "20140421-185622" //批次ID
							}
						]
					}
				});
			});

			//(2)查询，获得结果
			app.get('/si/shquit/list', function(req, res){
				res.json({
					"status" : "ok",
					"message" : "",
					"data" : {
						"totalCount" : 120,   //总记录条数
						"pageNo" : 2,         //当前页号
						"pageSize" : 20, //每页记录数
						"resultList" : [
							{
								"userCode" : 88888, //工号
								"userName" : "张三", //姓名
								"orgId" : 99999, //部门
								"orgName" : "静安A组", //部门
								"newJoinDate" : 1300220000000, //入职时间
								"leaveDate" : 1400220000000, //离职日期
								"credentialsNo" : "320102198612019921", //身份证号
								"censusAddr" : "江西省九江市九江县新馆乡四华村四组27号", //户口所在地
								"id" : 11  //记录ID
							},
							{
								"userCode" : 88888, //工号
								"userName" : "李四", //姓名
								"orgId" : 99999, //部门
								"orgName" : "静安A组", //部门
								"newJoinDate" : 1300220000000, //入职时间
								"leaveDate" : 1400220000000, //离职日期
								"credentialsNo" : "320102198612019921", //身份证号
								"censusAddr" : "上海市四平路1999号49-89-201", //户口所在地
								"id" : 22  //记录ID
							}
						]
					}
				});
			});

			//(3)确认退出
			app.post('/si/shquit/confirmquit', function(req, res){
				res.json({
					"status" : "ok",
					"message" : ""
				});
			});


		/** 页面：上海自缴 **/
			//(1)查询，获得结果
			app.get('/si/self/list', function(req, res){
				res.json({
					"status" : "ok",
					"message" : "",
					"data" : {
						"totalCount" : 120,   //总记录条数
						"pageNo" : 2,         //当前页号
						"pageSize" : 20, //每页记录数
						"resultList" : [
							{
								"userCode" : 88888, //工号
								"userName" : "张三", //姓名
								"orgId" : 99999, //部门
								"orgName" : "静安A组", //部门
								"paymentBase" : 3022, //自缴基数
								"paymentBaseId" : 1, //自缴基数Id
								"paymentMoney" : 3022.00, //自缴总金额
								"companyMoney" : 2022.00, //公司承担金额
								"personalMoney" : 1000.00, //个人承担金额
								"payBeginDate" : 1402557950677, //自缴起始月份
								"payEndDate" : 1402557950677, //自缴结束月份
								"extraBeginDate" : 1402557950677, //自缴补缴周期-起
								"extraEndDate" : 1402557950677, //自缴补缴周期-止
								"createTime" : 1402557950677, //创建日期
								"changeTypeId" : 0, //新进或退出状态 0-新进，1-退出（用在编辑和作废判断上）
								"id" : 11  //记录ID
							},
							{
								"userCode" : 88880, //工号
								"userName" : "李四", //姓名
								"orgId" : 99999, //部门
								"orgName" : "经纪研发中心", //部门
								"paymentBase" : 2047, //自缴基数
								"paymentBaseId" : 2, //自缴基数Id
								"paymentMoney" : 2047.00, //自缴总金额
								"companyMoney" : 1500.00, //公司承担金额
								"personalMoney" : 547.00, //个人承担金额
								"payBeginDate" : 1402557950677, //自缴起始月份
								"payEndDate" : 1402557950677, //自缴结束月份
								"extraBeginDate" : 1362557950677, //自缴补缴周期-起
								"extraEndDate" : 1402667950677, //自缴补缴周期-止
								"createTime" : 1402557950677, //创建日期
								"changeTypeId" : 1, //新进或退出状态 0-新进，1-退出（用在编辑和作废判断上）
								"id" : 22  //记录ID
							},
							{
								"userCode" : 88881, //工号
								"userName" : "王五", //姓名
								"orgId" : 99999, //部门
								"orgName" : "经纪研发中心", //部门
								"paymentBase" : 2047, //自缴基数
								"paymentBaseId" : 2, //自缴基数Id
								"paymentMoney" : 2047.00, //自缴总金额
								"companyMoney" : 1500.00, //公司承担金额
								"personalMoney" : 547.00, //个人承担金额
								"payBeginDate" : 1402557950677, //自缴起始月份
								"payEndDate" : 1402557950677, //自缴结束月份
								"extraBeginDate" : 1362557950677, //自缴补缴周期-起
								"extraEndDate" : 1402667950677, //自缴补缴周期-止
								"createTime" : 1402557950677, //创建日期
								"changeTypeId" : null, //新进或退出状态 0-新进，1-退出（用在编辑和作废判断上）
								"id" : 33  //记录ID
							}
						]
					}
				})
			});

			//列表操作 - 作废
			app.post('/si/self/abolish', function(req, res){
				res.json({
					"status" : "ok",
					"message" : "此记录已经进入上海新进/退出名单，不可作废。"
				})
			});

			//新增自缴
			app.post('/si/self/add', function(req, res){
				res.json({
					"status" : "ok",
					"message" : ""
				})
			});

			//列表操作 - 编辑
			app.post('/si/self/submit', function(req, res){
				res.json({
					"status" : "ok",
					"message" : ""
				})
			});



		/** 页面：宁波新进 **/
			//(1)切换缴纳年月，联动缴纳批次，以及拉名称、确认退出状态
			app.get('/si/nbnew/switchdate', function(req, res){
				res.json({
					"status" : "ok",
					"message" : "",
					"data" : {
						"batchInfo" : {//缴纳批次
							"confirmStatus" : 0,//是否已确认缴纳 0-未确认，1-已确认
							"batchId" : "20140407-152351" //批次ID
						}
					}
				});
			});

			//(2)查询，获得结果
			app.get('/si/nbnew/list', function(req, res){
				res.json({
					"status" : "ok",
					"message" : "",
					"data" : {
						"totalCount" : 120,   //总记录条数
						"pageNo" : 2,         //当前页号
						"pageSize" : 20, //每页记录数
						"resultList" : [
							{
								"userCode" : 88888, //工号
								"orgId" : 99999, //部门ID
								"orgName" : "静安A组", //部门
								"userName" : "张三", //姓名
								"sex" : "女", //性别
								"censusId" : 0, //1,3-城镇，0,4-农村（前端处理一下）
								"companyAddr" : "上海市东方有线大厦28楼", //异地地址（办理异地就医用到，原则上是单位所在地）
								"credentialsNo" : "310023198912029981", //身份证号码
								"companyPhone" : "021-33242234", //联系电话
								"paymentDate" : 1402557950677, //委托时间
								"paymentBase" : 3022, //社保基数
								"requireDate" : 1402557950677, //要求参保起始月份
								"comment" : "备注内容", //备注
								"contractEnd" : 1, //是否深圳外包到期 1-到期
								"applyStatus" : 0, //是否失败  0-失败，1-成功
								"failureReason" : "上家未退", //失败原因
								"id" : 11  //记录ID
							},
							{
								"userCode" : 88880, //工号
								"orgId" : 99999, //部门ID
								"orgName" : "静安A组", //部门
								"userName" : "李四", //姓名
								"sex" : "女", //性别
								"censusId" : 0, //1,3-城镇，0,4-农村（前端处理一下）
								"companyAddr" : "上海市东方有线大厦28楼", //异地地址（办理异地就医用到，原则上是单位所在地）
								"credentialsNo" : "5324534535", //身份证号码
								"companyPhone" : "021-33242234", //联系电话
								"paymentDate" : 1402557950677, //委托时间
								"paymentBase" : 3022, //社保基数
								"requireDate" : 1402557950677, //要求参保起始月份
								"comment" : "备注内容", //备注
								"contractEnd" : 0, //是否深圳外包到期 1-到期
								"applyStatus" : 1, //是否失败 0-失败，1-成功
								"failureReason" : "", //失败原因
								"id" : 22  //记录ID
							},
							{
								"userCode" : 88880, //工号
								"orgId" : 99999, //部门ID
								"orgName" : "静安A组", //部门
								"userName" : "李四", //姓名
								"sex" : "女", //性别
								"censusId" : 0, //1,3-城镇，0,4-农村（前端处理一下）
								"companyAddr" : "上海市东方有线大厦28楼", //异地地址（办理异地就医用到，原则上是单位所在地）
								"credentialsNo" : "5324534535", //身份证号码
								"companyPhone" : "021-33242234", //联系电话
								"paymentDate" : 1402557950677, //委托时间
								"paymentBase" : null, //社保基数
								"requireDate" : 1402557950677, //要求参保起始月份
								"comment" : "", //备注
								"contractEnd" : 0, //是否深圳外包到期 1-到期
								"applyStatus" : null, //是否失败 0-失败，1-成功
								"failureReason" : "", //失败原因
								"id" : 33  //记录ID
							}
						]
					}
				});
			});

			//(3)确认缴纳
			app.post('/si/nbnew/confirmpayment', function(req, res){
				res.json({
					"status" : "ok",
					"message" : ""
				});
			});

			//(4)拉名单--第一步：返回该批次的开始时间
			app.get('/si/nbnew/gbbegintime', function(req, res){
				res.json({
					"status" : "ok",
					"message" : "",
					"data" : {
						"batchBeginTime" : 1400220000000, //批次开始时间
					}
				});
			});

			//(4)拉名单--第二步：拉名单操作
			app.post('/si/nbnew/generatebatch', function(req, res){
				res.json({
					"status" : "ok",
					"message" : ""
				});
			});

			//列表操作 - 编辑
			app.post('/si/nbnew/edit', function(req, res){
				res.json({
					"status" : "ok",
					"message" : ""
				});
			});


		/** 页面：宁波退出 **/
			//(1)切换退出年月，联动退出批次，以及拉名称、确认退出状态
			app.get('/si/nbquit/switchdate', function(req, res){
				res.json({
					"status" : "ok",
					"message" : "",
					"data" : {
						"batchInfo" : {
							"confirmStatus" : 0,//是否已确认缴纳 0-未确认，1-已确认
							"batchId" : "20140407-152351" //批次ID
						}
					}
				});
			});

			//(2)查询，获得结果
			app.get('/si/nbquit/list', function(req, res){
				res.json({
					"status" : "ok",
					"message" : "",
					"data" : {
						"totalCount" : 120,   //总记录条数
						"pageNo" : 2,         //当前页号
						"pageSize" : 20, //每页记录数
						"resultList" : [
							{
								"userCode" : 88888, //工号
								"userName" : "张三", //姓名
								"credentialsNo" : "320102198612019921", //身份证号码
								"paymentDate" : 1402557950677, //截止时间
								"id" : 11  //记录ID
							},
							{
								"userCode" : 99999, //工号
								"userName" : "李四", //姓名
								"credentialsNo" : "310092198009289087", //身份证号码
								"paymentDate" : 1402557950677, //截止时间
								"id" : 11  //记录ID
							}
						]
					}
				});
			});

			//(3)确认退出
			app.post('/si/nbquit/confirmquit', function(req, res){
				res.json({
					"status" : "ok",
					"message" : ""
				});
			});

			//(4)拉名单--第一步：返回该批次的开始时间
			app.get('/si/nbquit/gbbegintime', function(req, res){
				res.json({
					"status" : "ok",
					"message" : "",
					"data" : {
						"batchBeginTime" : 1400220000000, //批次开始时间
					}
				});
			});

			//(4)拉名单--第二步：拉名单操作
			app.post('/si/nbquit/generatebatch', function(req, res){
				res.json({
					"status" : "ok",
					"message" : ""
				});
			});


		/** 页面：深圳退出 **/
			//(1)切换退出年月，联动退出批次，以及拉名称、确认退出状态
			app.get('/si/szquit/switchdate', function(req, res){
				res.json({
					"status" : "ok",
					"message" : "",
					"data" : {
						"batchInfo" : [//退出批次下拉框选项
							{
								"confirmStatus" : 0,//是否已确认缴纳 0-未确认，1-已确认
								"batchId" : "20140407-152351" //批次ID
							}/*,
							{
								"confirmStatus" : 0,//是否已确认缴纳 0-未确认，1-已确认
								"batchId" : "20140421-185622" //批次ID
							}*/
						]
					}
				});
			});

			//(2)查询，获得结果
			app.get('/si/szquit/list', function(req, res){
				res.json({
					"status" : "ok",
					"message" : "",
					"data" : {
						"totalCount" : 120,   //总记录条数
						"pageNo" : 2,         //当前页号
						"pageSize" : 20, //每页记录数
						"resultList" : [
							{
								"orgId" : 99999, //部门ID
								"orgName" : "静安A组", //部门
								"userCode" : 88888, //工号
								"userName" : "张三", //姓名
								"credentialsNo" : "320102198612019921", //身份证号码
								"paymentDate" : 1402557950677, //停保年月
								"comment" : "备注xxx", //备注
								"id" : 11  //记录ID
							},
							{
								"orgId" : 99990, //部门ID
								"orgName" : "静安C组", //部门
								"userCode" : 88880, //工号
								"userName" : "李四", //姓名
								"credentialsNo" : "320102198612019921", //身份证号码
								"paymentDate" : 1402557950677, //停保年月
								"comment" : "", //备注
								"id" : 11  //记录ID
							}
						]
					}
				});
			});

			//(3)确认退出
			app.post('/si/szquit/confirmquit', function(req, res){
				res.json({
					"status" : "ok",
					"message" : ""
				});
			});

			//(4)拉名单--第一步：返回该批次的开始时间
			app.get('/si/szquit/gbbegintime', function(req, res){
				res.json({
					"status" : "ok",
					"message" : "",
					"data" : {
						"batchBeginTime" : 1400220000000, //批次开始时间
					}
				});
			});

			//(4)拉名单--第二步：拉名单操作
			app.post('/si/szquit/generatebatch', function(req, res){
				res.json({
					"status" : "ok",
					"message" : ""
				});
			});
}