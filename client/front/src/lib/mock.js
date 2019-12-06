const mock = {
    loginSuccess: {
        "msg": {
            "registrationLevels": {
                0: {"fee": 0.0, "limit": 0, "id": 0},
                1: {"fee": 20.0, "limit": 120, "id": 1},
                2: {"fee": 60.0, "limit": 80, "id": 2},
                3: {"fee": 100.0, "limit": 40, "id": 3}
            },
            "doctors": {
                4: {"name": "红红红", "id": 4, "department": 4, "title": 1},
                5: {"name": "绿绿绿", "id": 5, "department": 4, "title": 2},
                6: {"name": "黄黄黄", "id": 6, "department": 4, "title": 3},
                7: {"name": "青青青", "id": 7, "department": 5, "title": 1},
                8: {"name": "紫紫紫", "id": 8, "department": 5, "title": 1}
            },
            "titles": {
                0: {"registrationLevel": 0, "name": "住院医师", "id": 0},
                1: {"registrationLevel": 1, "name": "主治医师", "id": 1},
                2: {"registrationLevel": 2, "name": "副主任医师", "id": 2},
                3: {"registrationLevel": 3, "name": "主任医师", "id": 3},
                4: {"registrationLevel": 0, "name": "初级技师", "id": 4},
                5: {"registrationLevel": 0, "name": "主管技师", "id": 5},
                6: {"registrationLevel": 0, "name": "副主任技师", "id": 6},
                7: {"registrationLevel": 0, "name": "主任技师", "id": 7},
                8: {"registrationLevel": 0, "name": "初级药师", "id": 8},
                9: {"registrationLevel": 0, "name": "主管药师", "id": 9},
                10: {"registrationLevel": 0, "name": "副主任药师", "id": 10},
                11: {"registrationLevel": 0, "name": "主任药师", "id": 11},
                12: {"registrationLevel": 0, "name": "前台接诊", "id": 12}
            },
            "departments": {
                0: {"name": "前台", "id": 0, "clazz": 3},
                1: {"name": "中药药房", "id": 1, "clazz": 1},
                2: {"name": "西药药房", "id": 2, "clazz": 1},
                3: {"name": "放射科", "id": 3, "clazz": 2},
                4: {"name": "外科", "id": 4, "clazz": 0},
                5: {"name": "消化科", "id": 5, "clazz": 0}
            },
            "inspectionItems": {
                0: {"code": "PTTS", "departmentID": 3, "fee": 5.0, "id": 0, "name": "普通透视"},
                1: {"code": "SGBCTS", "departmentID": 3, "fee": 15.0, "id": 1, "name": "食管钡餐透视"}
            },
            "user": {"name": "白白白", "title": 12, "department": 0}
        }, "status": 0
    },
    loginDoctorSuccess: {
        "msg": {
            "medicines": {
                1: {"id": 1, "name": "亚硝酸钠", "format": "500g×1瓶", "price": "3.84", "code": "YXSN"},
                2: {"id": 2, "name": "砂仁", "format": "1000mg/g", "price": "47.23", "code": "SR"},
                3: {"id": 3, "name": "注射用去铁胺(得斯芬)", "format": "0.5g×1支", "price": "3.22", "code": "ZSYQTA(DSF)"},
                4: {"id": 4, "name": "乳宁颗粒", "format": "15g×9袋/盒", "price": "8.29", "code": "RNKL"},
                5: {"id": 5, "name": "当飞利肝宁胶囊", "format": "0.25g×36粒/盒", "price": "39.08", "code": "DFLGNJN"},
                6: {
                    "id": 6,
                    "name": "右旋糖酐40葡萄糖(塑瓶)注射液",
                    "format": "500ml:30g:25g×1瓶",
                    "price": "28.71",
                    "code": "YXTG40PTT(SP)ZSY"
                },
                7: {"id": 7, "name": "尼莫地平片", "format": "20mg×50片/瓶", "price": "0.29", "code": "NMDPP"},
                8: {
                    "id": 8,
                    "name": "盐酸甲氧氯普胺(胃复安)注射液",
                    "format": "10mg×10支/盒",
                    "price": "6.08",
                    "code": "YSJYLPA(WFA)ZSY"
                },
                9: {"id": 9, "name": "双氧水(30%)(AR)", "format": "500ml×1瓶", "price": "8.52", "code": "SYS(30%)(AR)"},
                10: {"id": 10, "name": "艾叶", "format": "1000mg/g", "price": "46.15", "code": "AY"},
                11: {"id": 11, "name": "八月扎", "format": "1000mg/g", "price": "62.1", "code": "BYZ"},
                12: {"id": 12, "name": "巴戟天", "format": "1000mg/g", "price": "2.59", "code": "BZT"},
                13: {"id": 13, "name": "白扁豆", "format": "1000mg/g", "price": "29.15", "code": "BBD"},
                14: {"id": 14, "name": "左西孟旦注射液", "format": "5ml:12.5mg×1支", "price": "9.57", "code": "ZXMDZSY"},
                15: {"id": 15, "name": "枫蓼肠胃康颗粒", "format": "8g×9袋/盒", "price": "3.64", "code": "FZCWKKL"},
                16: {"id": 16, "name": "复合维生素片(爱乐维-拜耳)", "format": "30片/盒", "price": "11.8", "code": "FHWSSP(ALW-BE)"},
                17: {"id": 17, "name": "燀桃仁颗粒", "format": "1g/10g/袋", "price": "73.23", "code": "TRKL"},
                18: {"id": 18, "name": "克痒舒洗液", "format": "120ml×1瓶", "price": "38.78", "code": "KYSXY"},
                19: {"id": 19, "name": "防风颗粒", "format": "1g/6g/袋", "price": "22.57", "code": "FFKL"},
                20: {
                    "id": 20,
                    "name": "小牛血去蛋白提取物眼用凝胶",
                    "format": "20%:5g×1支",
                    "price": "25.14",
                    "code": "XNXQDBTQWYYNJ"
                },
                21: {"id": 21, "name": "小儿消积止咳口服液", "format": "10ml*6支/盒", "price": "15.17", "code": "XEXJZKKFY"},
                22: {"id": 22, "name": "尿毒清颗粒", "format": "5g*18袋/盒", "price": "21.52", "code": "NDQKL"},
                23: {
                    "id": 23,
                    "name": "右旋糖酐40葡萄糖注射液",
                    "format": "6%500ml×1瓶",
                    "price": "20.18",
                    "code": "YXTG40PTTZSY"
                },
                24: {
                    "id": 24,
                    "name": "门冬氨酸鸟氨酸注射液(雅博司)",
                    "format": "5g×5支/盒",
                    "price": "18.51",
                    "code": "MDASNASZSY(YBS)"
                }
            },
            "registrationLevels": {
                0: {"fee": 0.0, "limit": 0, "id": 0},
                1: {"fee": 20.0, "limit": 120, "id": 1},
                2: {"fee": 60.0, "limit": 80, "id": 2},
                3: {"fee": 100.0, "limit": 40, "id": 3}
            },
            "patients": [{
                "id": 1123333333333,
                "name": "爱新觉罗",
                "gender": 0,
                "birthday": 390240000000,
                "medicalRecord": 1569007490943
            }],
            "diseases": {
                1: {"id": 1, "code": "ZDXFX", "name": "中毒性腹泻", "clazz": 1},
                2: {"id": 2, "code": "ZDXFX", "name": "中毒性腹泻", "clazz": 1},
                3: {"id": 3, "code": "ZDXCY", "name": "中毒性肠炎", "clazz": 1},
                4: {"id": 4, "code": "ZDXJCY", "name": "中毒性结肠炎", "clazz": 1},
                5: {"id": 5, "code": "ZDXWCY", "name": "中毒性胃肠炎", "clazz": 1},
                6: {"id": 6, "code": "GMXCY", "name": "过敏性肠炎", "clazz": 1},
                7: {"id": 7, "code": "GMXFX", "name": "过敏性腹泻", "clazz": 1},
                8: {"id": 8, "code": "GMXJCY", "name": "过敏性结肠炎", "clazz": 1},
                9: {"id": 9, "code": "WCDGMZ", "name": "胃肠道过敏症", "clazz": 1},
                10: {"id": 10, "code": "YSXFX", "name": "饮食性腹泻", "clazz": 1},
                11: {"id": 11, "code": "YSXWCY", "name": "饮食性胃肠炎", "clazz": 1},
                12: {"id": 12, "code": "SSLXBXWCY", "name": "嗜酸粒细胞性胃肠炎", "clazz": 1},
                13: {"id": 13, "code": "YWXGXCY", "name": "药物相关性肠炎", "clazz": 1},
                14: {"id": 14, "code": "YYXFX", "name": "胰源性腹泻", "clazz": 1},
                15: {"id": 15, "code": "JMXJCY", "name": "假膜性结肠炎", "clazz": 1},
                16: {"id": 16, "code": "CY", "name": "肠炎", "clazz": 1},
                17: {"id": 17, "code": "CXXJCY", "name": "出血性结肠炎", "clazz": 1},
                18: {"id": 18, "code": "FGRXFX", "name": "非感染性腹泻", "clazz": 1},
                19: {"id": 19, "code": "FX", "name": "腹泻", "clazz": 1},
                20: {"id": 20, "code": "JXCY", "name": "急性肠炎", "clazz": 1},
                21: {"id": 21, "code": "JXCXXCY", "name": "急性出血性肠炎", "clazz": 1},
                22: {"id": 22, "code": "JXJCY", "name": "急性结肠炎", "clazz": 1},
                23: {"id": 23, "code": "JXWCY", "name": "急性胃肠炎", "clazz": 1},
                24: {"id": 24, "code": "JCY", "name": "结肠炎", "clazz": 1},
                25: {"id": 25, "code": "LXXCY", "name": "流行性肠炎", "clazz": 1},
                26: {"id": 26, "code": "MXCY", "name": "慢性肠炎", "clazz": 1},
                27: {"id": 27, "code": "MXFX", "name": "慢性腹泻", "clazz": 1},
                28: {"id": 28, "code": "MXJCY", "name": "慢性结肠炎", "clazz": 1},
                29: {"id": 29, "code": "MXWCY", "name": "慢性胃肠炎", "clazz": 1},
                30: {"id": 30, "code": "NDXCY", "name": "脓毒性肠炎", "clazz": 1},
                31: {"id": 31, "code": "QJFX", "name": "秋季腹泻", "clazz": 1},
                32: {"id": 32, "code": "WMXCY", "name": "伪膜性肠炎", "clazz": 1},
                33: {"id": 33, "code": "WCYCXXJX", "name": "胃肠炎(出血性)(急性)", "clazz": 1},
                34: {"id": 34, "code": "XECY", "name": "小儿肠炎", "clazz": 1},
                35: {"id": 35, "code": "YZJCY", "name": "乙状结肠炎", "clazz": 1}
            },
            "titles": {
                0: {"registrationLevel": 0, "name": "住院医师", "id": 0},
                1: {"registrationLevel": 1, "name": "主治医师", "id": 1},
                2: {"registrationLevel": 2, "name": "副主任医师", "id": 2},
                3: {"registrationLevel": 3, "name": "主任医师", "id": 3},
                4: {"registrationLevel": 0, "name": "初级技师", "id": 4},
                5: {"registrationLevel": 0, "name": "主管技师", "id": 5},
                6: {"registrationLevel": 0, "name": "副主任技师", "id": 6},
                7: {"registrationLevel": 0, "name": "主任技师", "id": 7},
                8: {"registrationLevel": 0, "name": "初级药师", "id": 8},
                9: {"registrationLevel": 0, "name": "主管药师", "id": 9},
                10: {"registrationLevel": 0, "name": "副主任药师", "id": 10},
                11: {"registrationLevel": 0, "name": "主任药师", "id": 11},
                12: {"registrationLevel": 0, "name": "前台接诊", "id": 12}
            },
            "departments": {
                0: {"name": "前台", "id": 0, "clazz": 3},
                1: {"name": "中药药房", "id": 1, "clazz": 1},
                2: {"name": "西药药房", "id": 2, "clazz": 1},
                3: {"name": "放射科", "id": 3, "clazz": 2},
                4: {"name": "外科", "id": 4, "clazz": 0},
                5: {"name": "消化科", "id": 5, "clazz": 0}
            },
            "user": {"name": "绿绿绿", "title": 2, "department": 4},
            "inspectionItems": {
                0: {"code": "PTTS", "departmentID": 3, "fee": 5.0, "name": "普通透视", "id": 0},
                1: {"code": "SGBCTS", "departmentID": 3, "fee": 15.0, "name": "食管钡餐透视", "id": 1}
            }
        }, "status": 0
    },
    loginPharmacySuccess: {
        "msg": {
            "medicines": {
                1: {"code": "YXSN", "price": "3.84", "name": "亚硝酸钠", "format": "500g×1瓶", "id": 1},
                2: {"code": "SR", "price": "47.23", "name": "砂仁", "format": "1000mg/g", "id": 2},
                3: {"code": "ZSYQTA(DSF)", "price": "3.22", "name": "注射用去铁胺(得斯芬)", "format": "0.5g×1支", "id": 3},
                4: {"code": "RNKL", "price": "8.29", "name": "乳宁颗粒", "format": "15g×9袋/盒", "id": 4},
                5: {"code": "DFLGNJN", "price": "39.08", "name": "当飞利肝宁胶囊", "format": "0.25g×36粒/盒", "id": 5},
                6: {
                    "code": "YXTG40PTT(SP)ZSY",
                    "price": "28.71",
                    "name": "右旋糖酐40葡萄糖(塑瓶)注射液",
                    "format": "500ml:30g:25g×1瓶",
                    "id": 6
                },
                7: {"code": "NMDPP", "price": "0.29", "name": "尼莫地平片", "format": "20mg×50片/瓶", "id": 7},
                8: {
                    "code": "YSJYLPA(WFA)ZSY",
                    "price": "6.08",
                    "name": "盐酸甲氧氯普胺(胃复安)注射液",
                    "format": "10mg×10支/盒",
                    "id": 8
                },
                9: {"code": "SYS(30%)(AR)", "price": "8.52", "name": "双氧水(30%)(AR)", "format": "500ml×1瓶", "id": 9},
                10: {"code": "AY", "price": "46.15", "name": "艾叶", "format": "1000mg/g", "id": 10},
                11: {"code": "BYZ", "price": "62.1", "name": "八月扎", "format": "1000mg/g", "id": 11},
                12: {"code": "BZT", "price": "2.59", "name": "巴戟天", "format": "1000mg/g", "id": 12},
                13: {"code": "BBD", "price": "29.15", "name": "白扁豆", "format": "1000mg/g", "id": 13},
                14: {"code": "ZXMDZSY", "price": "9.57", "name": "左西孟旦注射液", "format": "5ml:12.5mg×1支", "id": 14},
                15: {"code": "FZCWKKL", "price": "3.64", "name": "枫蓼肠胃康颗粒", "format": "8g×9袋/盒", "id": 15},
                16: {"code": "FHWSSP(ALW-BE)", "price": "11.8", "name": "复合维生素片(爱乐维-拜耳)", "format": "30片/盒", "id": 16},
                17: {"code": "TRKL", "price": "73.23", "name": "燀桃仁颗粒", "format": "1g/10g/袋", "id": 17},
                18: {"code": "KYSXY", "price": "38.78", "name": "克痒舒洗液", "format": "120ml×1瓶", "id": 18},
                19: {"code": "FFKL", "price": "22.57", "name": "防风颗粒", "format": "1g/6g/袋", "id": 19},
                20: {
                    "code": "XNXQDBTQWYYNJ",
                    "price": "25.14",
                    "name": "小牛血去蛋白提取物眼用凝胶",
                    "format": "20%:5g×1支",
                    "id": 20
                },
                21: {"code": "XEXJZKKFY", "price": "15.17", "name": "小儿消积止咳口服液", "format": "10ml*6支/盒", "id": 21},
                22: {"code": "NDQKL", "price": "21.52", "name": "尿毒清颗粒", "format": "5g*18袋/盒", "id": 22},
                23: {
                    "code": "YXTG40PTTZSY",
                    "price": "20.18",
                    "name": "右旋糖酐40葡萄糖注射液",
                    "format": "6%500ml×1瓶",
                    "id": 23
                },
                24: {
                    "code": "MDASNASZSY(YBS)",
                    "price": "18.51",
                    "name": "门冬氨酸鸟氨酸注射液(雅博司)",
                    "format": "5g×5支/盒",
                    "id": 24
                }
            },
            "registrationLevels": {
                0: {"fee": 0.0, "limit": 0, "id": 0},
                1: {"fee": 20.0, "limit": 120, "id": 1},
                2: {"fee": 60.0, "limit": 80, "id": 2},
                3: {"fee": 100.0, "limit": 40, "id": 3}
            },
            "diseases": [{
                "code": "nkjb",
                "children": [{
                    "code": "hxnk",
                    "children": [{"code": "xc", "children": [], "name": "哮喘", "id": 4, "clazz": 1}],
                    "name": "呼吸内科",
                    "id": 1,
                    "clazz": 1
                }, {
                    "code": "xhnk",
                    "children": [{
                        "code": "cy",
                        "children": [{"code": "cxxjcy", "children": [], "name": "出血性结肠炎", "id": 6, "clazz": 1}],
                        "name": "肠炎",
                        "id": 5,
                        "clazz": 1
                    }],
                    "name": "消化内科",
                    "id": 2,
                    "clazz": 1
                }, {
                    "code": "xxgnk",
                    "children": [{"code": "xjy", "children": [], "name": "心肌炎", "id": 7, "clazz": 1}],
                    "name": "心血管内科",
                    "id": 3,
                    "clazz": 1
                }],
                "name": "内科疾病",
                "id": 0,
                "clazz": 1
            }],
            "titles": {
                0: {"registrationLevel": 0, "name": "住院医师", "id": 0},
                1: {"registrationLevel": 1, "name": "主治医师", "id": 1},
                2: {"registrationLevel": 2, "name": "副主任医师", "id": 2},
                3: {"registrationLevel": 3, "name": "主任医师", "id": 3},
                4: {"registrationLevel": 0, "name": "初级技师", "id": 4},
                5: {"registrationLevel": 0, "name": "主管技师", "id": 5},
                6: {"registrationLevel": 0, "name": "副主任技师", "id": 6},
                7: {"registrationLevel": 0, "name": "主任技师", "id": 7},
                8: {"registrationLevel": 0, "name": "初级药师", "id": 8},
                9: {"registrationLevel": 0, "name": "主管药师", "id": 9},
                10: {"registrationLevel": 0, "name": "副主任药师", "id": 10},
                11: {"registrationLevel": 0, "name": "主任药师", "id": 11},
                12: {"registrationLevel": 0, "name": "前台接诊", "id": 12},
                13: {"registrationLevel": 0, "name": "管理员", "id": 13}
            },
            "departments": {
                0: {"name": "前台", "id": 0, "clazz": 3},
                1: {"name": "中药药房", "id": 1, "clazz": 1},
                2: {"name": "西药药房", "id": 2, "clazz": 1},
                3: {"name": "放射科", "id": 3, "clazz": 2},
                4: {"name": "普通外科", "id": 4, "clazz": 0},
                5: {"name": "消化内科", "id": 5, "clazz": 0},
                8: {"name": "管理员", "id": 8, "clazz": 8}
            },
            "user": {"name": "灰灰灰", "title": 8, "department": 2}
        }, "status": 0
    },
    loginAdminSuccess: {
        "msg": {
            "registrationLevels": {
                0: {"fee": 0.0, "limit": 0, "id": 0},
                1: {"fee": 20.0, "limit": 120, "id": 1},
                2: {"fee": 60.0, "limit": 80, "id": 2},
                3: {"fee": 100.0, "limit": 40, "id": 3}
            },
            "titles": {
                0: {"registrationLevel": 0, "name": "住院医师", "id": 0},
                1: {"registrationLevel": 1, "name": "主治医师", "id": 1},
                2: {"registrationLevel": 2, "name": "副主任医师", "id": 2},
                3: {"registrationLevel": 3, "name": "主任医师", "id": 3},
                4: {"registrationLevel": 0, "name": "初级技师", "id": 4},
                5: {"registrationLevel": 0, "name": "主管技师", "id": 5},
                6: {"registrationLevel": 0, "name": "副主任技师", "id": 6},
                7: {"registrationLevel": 0, "name": "主任技师", "id": 7},
                8: {"registrationLevel": 0, "name": "初级药师", "id": 8},
                9: {"registrationLevel": 0, "name": "主管药师", "id": 9},
                10: {"registrationLevel": 0, "name": "副主任药师", "id": 10},
                11: {"registrationLevel": 0, "name": "主任药师", "id": 11},
                12: {"registrationLevel": 0, "name": "前台接诊", "id": 12},
                13: {"registrationLevel": 0, "name": "管理员", "id": 13}
            },
            "departments": {
                0: {"name": "前台", "id": 0, "clazz": 3},
                1: {"name": "中药药房", "id": 1, "clazz": 1},
                2: {"name": "西药药房", "id": 2, "clazz": 1},
                3: {"name": "放射科", "id": 3, "clazz": 2},
                4: {"name": "外科", "id": 4, "clazz": 0},
                5: {"name": "消化科", "id": 5, "clazz": 0},
                8: {"name": "管理员", "id": 8, "clazz": 8}
            },
            "user": {"name": "哈哈哈", "title": 13, "department": 8}
        }, "status": 0
    }

    ,
    registerWithNewMedicalRecordSuccess: {
        "msg": {"registrationID": 1568916278621, "medicalRecordID": 1568916278620, "cost": 100},
        "status": 0
    },
    registerWithExistMedicalRecordSuccess: {
        "msg": {"registrationID": 1568916278621, "medicalRecordID": 1568916278620, "cost": 100},
        "status": 0
    },
    cancelSuccess: {
        "msg": "退号成功",
        "status": 0
    },
    queryMedicalRecordByIDSuccess: {
        "msg": {
            "birthday": 1558916278620,
            "address": "北京",
            "gender": 0,
            "name": "爱新觉罗",
            "id": 1568976155713,
            "IDNumber": "011110011110011110"
        }, "status": 0
    },
    queryCancelableByMedicalRecordID: {
        "msg": [
            {
                "id": 100,
                "time": 1569005142057,
                "doctor": 4,
                "department": 4,
                "cost": 21,
                "status": 1
            }
        ], "status": 0
    },
    queryMedicalRecordInfo: {
        "msg": {
            "birthday": 275068800000,
            "presentIllnessHistory": "",
            "pastHistory": "",
            "gender": 0,
            "allergyHistory": "",
            "name": "爱新觉罗",
            "id": 1569081463380,
            "currentIllnessTreatment": ""
        }, "status": 0
    },
    updateMedicalRecordInfoSuccess: {
        "msg": '保存成功',
        "status": 0
    },
    fetchPrescriptionSuccess:
        {
            "msg": [{
                "clazz": 1,
                "fee": 11.51,
                "id": 1569396211728,
                "medicalRecordID": 1569132500520,
                "medicineList": [{
                    "amount": 1,
                    "dosage": "1",
                    "frequency": "1",
                    "medicineID": 3,
                    "usage": "1"
                }, {"amount": 1, "dosage": "1", "frequency": "1", "medicineID": 4, "usage": "1"}],
                "name": "sda",
                "registrationID": 1569395855681,
                "status": 0
            }], "status": 0
        }


}


export default mock;
