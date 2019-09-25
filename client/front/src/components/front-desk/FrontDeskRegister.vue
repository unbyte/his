<template>
    <div id="front-desk-register" class="tab-page">
        <!--步骤条-->
        <mu-row gutter class="step-bar">
            <mu-col span="1">
                <mu-button flat :disabled="controls.activeStep === 0" @click="handlePrev"
                           v-show="controls.activeStep>0&&controls.activeStep<3"> 上一步
                </mu-button>
            </mu-col>
            <mu-col span="10">
                <mu-stepper :active-step="controls.activeStep">
                    <mu-step>
                        <mu-step-label>
                            病历
                        </mu-step-label>
                    </mu-step>
                    <mu-step>
                        <mu-step-label>
                            确认信息
                        </mu-step-label>
                    </mu-step>
                    <mu-step>
                        <mu-step-label>
                            挂号
                        </mu-step-label>
                    </mu-step>
                    <mu-step>
                        <mu-step-label>
                            完成
                        </mu-step-label>
                    </mu-step>
                </mu-stepper>
            </mu-col>
            <mu-col span="1">
                <mu-button color="primary" @click="handleNext"
                > {{nextButtonTitle}}
                </mu-button>
            </mu-col>
        </mu-row>

        <!--病历-->
        <mu-container v-show="controls.activeStep === 0" class="step-content">

            <div class="switch">
                <mu-switch v-model="controls.newMedicalRecord"
                           :label="controls.newMedicalRecord?'新建病历':'已有病历'" @change="switchForm"></mu-switch>
            </div>

            <div v-show="!controls.newMedicalRecord">
                <mu-form ref="existForm" :model="existForm" label-position="right" label-width="100">
                    <mu-form-item label="病历号" prop="medicalRecordID" :rules="formValidator.medicalRecordIDRules">
                        <mu-text-field max-length="13" v-model="existForm.medicalRecordID"
                                       :oninput="existForm.medicalRecordID = existForm.medicalRecordID.replace(/[^\d]/g, '')"
                                       prop="medicalRecordID"></mu-text-field>
                    </mu-form-item>
                </mu-form>
            </div>

            <div v-show="controls.newMedicalRecord">
                <mu-form :model="newForm" ref="newForm" label-position="right" label-width="100">
                    <mu-form-item prop="name" label="姓名" :rules="formValidator.requiredRules">
                        <mu-text-field prop="name" max-length="8" v-model="newForm.name"></mu-text-field>
                    </mu-form-item>
                    <mu-form-item prop="gender" label="性别" :rules="formValidator.requiredRules">
                        <mu-select v-model.number="newForm.gender" prop="gender">
                            <mu-option label="男" :value="0"></mu-option>
                            <mu-option label="女" :value="1"></mu-option>
                        </mu-select>
                    </mu-form-item>
                    <mu-form-item prop="birthday" label="出生日期" :rules="formValidator.requiredRules">
                        <mu-date-input prop="birthday" v-model="newForm.birthday" type="date" container="dialog"
                                       full-width
                                       :max-date="today"
                                       actions></mu-date-input>
                    </mu-form-item>
                    <mu-form-item prop="age" label="年龄">
                        <mu-text-field prop="age" :value="age" disabled></mu-text-field>
                    </mu-form-item>
                    <mu-form-item prop="IDNumber" label="身份证号码" :rules="formValidator.IDNumberRules">
                        <mu-text-field v-model="newForm.IDNumber" max-length="18" prop="IDNumber"></mu-text-field>
                    </mu-form-item>
                    <mu-form-item prop="address" label="家庭住址" :rules="formValidator.requiredRules">
                        <mu-text-field v-model="newForm.address" max-length="50" prop="address"></mu-text-field>
                    </mu-form-item>
                </mu-form>
            </div>
        </mu-container>

        <!--信息确认-->
        <mu-container v-show="controls.activeStep === 1" class="step-content">
            <mu-form :model="medicalRecordInfo" label-position="right" label-width="100">
                <mu-form-item prop="name" label="病历号">
                    <mu-text-field prop="name" :value="medicalRecordInfo.id" disabled></mu-text-field>
                </mu-form-item>
                <mu-form-item prop="name" label="姓名">
                    <mu-text-field prop="name" :value="medicalRecordInfo.name" disabled></mu-text-field>
                </mu-form-item>
                <mu-form-item prop="gender" label="性别">
                    <mu-select :value="medicalRecordInfo.gender" prop="gender" disabled>
                        <mu-option label="男" :value="0"></mu-option>
                        <mu-option label="女" :value="1"></mu-option>
                    </mu-select>
                </mu-form-item>
                <mu-form-item prop="birthday" label="出生日期">
                    <mu-date-input prop="birthday" :value="medicalRecordInfo.birthday" type="date" container="dialog"
                                   full-width
                                   :max-date="today"
                                   actions
                                   disabled></mu-date-input>
                </mu-form-item>
                <mu-form-item prop="age" label="年龄">
                    <mu-text-field prop="age" :value="getAge(medicalRecordInfo.birthday)" disabled></mu-text-field>
                </mu-form-item>
                <mu-form-item prop="IDNumber" label="身份证号码">
                    <mu-text-field :value="medicalRecordInfo.IDNumber" max-length="18" prop="IDNumber"
                                   disabled></mu-text-field>
                </mu-form-item>
                <mu-form-item prop="address" label="家庭住址">
                    <mu-text-field :value="medicalRecordInfo.address" max-length="50" prop="address"
                                   disabled></mu-text-field>
                </mu-form-item>
            </mu-form>
        </mu-container>

        <!--挂号-->
        <mu-container v-show="controls.activeStep === 2" class="step-content">
            <mu-form :model="registrationFilter" ref="registrationForm" label-position="right" label-width="100">
                <mu-row gutter>
                    <mu-col span="8">
                        <mu-row gutter>
                            <mu-col span="6">
                                <mu-form-item prop="department" label="科室">
                                    <mu-select prop="department" filterable
                                               v-model.number="registrationFilter.department"
                                               full-width>
                                        <mu-option label="全部" :value="-1"></mu-option>
                                        <mu-option v-for="department in filteredDepartments" :key="department.id"
                                                   :label="department.name"
                                                   :value="department.id"></mu-option>
                                    </mu-select>
                                </mu-form-item>
                            </mu-col>
                            <mu-col span="6">
                                <!--这里直接用职称来代替挂号级别了-->
                                <mu-form-item prop="title" label="级别">
                                    <mu-select prop="title" v-model.number="registrationFilter.title" full-width>
                                        <!--得加一个全部的选项，并且是默认值-->
                                        <mu-option label="全部" :value="-1"></mu-option>
                                        <mu-option v-for="title in filteredTitles" :key="title.id"
                                                   :label="title.name"
                                                   :value="title.id"></mu-option>
                                    </mu-select>
                                </mu-form-item>
                            </mu-col>
                        </mu-row>
                        <mu-row gutter>
                            <mu-col span="12">
                                <mu-form-item label="医生">
                                    <mu-data-table selectable :selects.sync="selects" checkbox :columns="doctorColumns"
                                                   :data="filteredDoctors"
                                                   @select-change="selectDoctor"
                                                   max-height="500">
                                        <template slot-scope="scope">
                                            <td>{{scope.row.name}}</td>
                                            <td class="is-right">{{$utils.titleIDToObject(scope.row.title).name}}</td>
                                            <td class="is-right">
                                                {{$utils.departmentIDToObject(scope.row.department).name}}
                                            </td>
                                            <td class="is-right">{{$utils.titleIDToLimit(scope.row.title)}}</td>
                                            <td class="is-right">
                                                {{`￥${$utils.titleIDToFee(scope.row.title).toFixed(2)}`}}
                                            </td>
                                        </template>
                                    </mu-data-table>
                                </mu-form-item>
                            </mu-col>
                        </mu-row>
                    </mu-col>

                    <mu-col span="3" offset="1">
                        <mu-list textline="two-line" class="select-info">
                            <mu-list-item button :ripple="false">
                                <mu-list-item-content>
                                    <mu-list-item-title>{{registrationForm.department === -1?
                                        '未选择':filteredDepartments[registrationForm.department].name}}
                                    </mu-list-item-title>
                                    <mu-list-item-sub-title>科室</mu-list-item-sub-title>
                                </mu-list-item-content>
                            </mu-list-item>
                            <mu-list-item button :ripple="false">
                                <mu-list-item-content>
                                    <mu-list-item-title>{{selects.length ===0 ?
                                        '未选择':registrationForm.doctor.name}}
                                    </mu-list-item-title>
                                    <mu-list-item-sub-title>医生</mu-list-item-sub-title>
                                </mu-list-item-content>
                            </mu-list-item>
                            <mu-list-item button :ripple="false">
                                <mu-list-item-content>
                                    <mu-list-item-title>{{registrationForm.title === -1 ?
                                        '未选择':filteredTitles[registrationForm.title].name
                                        }}
                                    </mu-list-item-title>
                                    <mu-list-item-sub-title>挂号级别</mu-list-item-sub-title>
                                </mu-list-item-content>
                            </mu-list-item>
                            <mu-list-item button :ripple="false">
                                <mu-list-item-content>
                                    <mu-list-item-title>
                                        {{controls.newMedicalRecord ?
                                        '￥1.00':'￥0.00' }}
                                    </mu-list-item-title>
                                    <mu-list-item-sub-title>工本费</mu-list-item-sub-title>
                                </mu-list-item-content>
                            </mu-list-item>
                            <mu-list-item button :ripple="false">
                                <mu-list-item-content>
                                    <mu-list-item-title>
                                        总计
                                    </mu-list-item-title>
                                </mu-list-item-content>
                                <mu-list-item-action style="font-size: 1.618em;">
                                    {{registrationForm.cost? `￥${registrationForm.cost.toFixed(2)}`: '未结算' }}
                                </mu-list-item-action>
                            </mu-list-item>
                        </mu-list>
                    </mu-col>
                </mu-row>
            </mu-form>

        </mu-container>

        <mu-container v-if="controls.activeStep === 3" class="step-content">
            <mu-row>
                <mu-col span="6" offset="3">
                    <mu-list class="success-info">
                        <mu-list-item :ripple="false">
                            <mu-list-item-content>
                                <mu-list-item-title>病历号
                                </mu-list-item-title>
                            </mu-list-item-content>
                            <mu-list-item-action>
                                <p style="display: flex;align-items: center;">
                                    <mu-badge content="New" color="primary" v-if="controls.newMedicalRecord"></mu-badge>
                                    {{responseInfo.medicalRecordID}}
                                </p>
                            </mu-list-item-action>
                        </mu-list-item>
                        <mu-list-item :ripple="false">
                            <mu-list-item-content>
                                <mu-list-item-title>挂号时间
                                </mu-list-item-title>
                            </mu-list-item-content>
                            <mu-list-item-action>
                                {{new Date(responseInfo.registrationID).toLocaleString('en')}}
                            </mu-list-item-action>
                        </mu-list-item>

                        <mu-list-item :ripple="false">

                            <mu-list-item-content>
                                <mu-list-item-title>
                                    科室
                                </mu-list-item-title>
                            </mu-list-item-content>
                            <mu-list-item-action>
                                {{filteredDepartments[registrationForm.department].name}}
                            </mu-list-item-action>
                        </mu-list-item>

                        <mu-list-item :ripple="false">
                            <mu-list-item-content>
                                <mu-list-item-title>医生
                                </mu-list-item-title>
                            </mu-list-item-content>
                            <mu-list-item-action>
                                {{registrationForm.doctor.name}}
                            </mu-list-item-action>
                        </mu-list-item>
                        <mu-list-item :ripple="false">
                            <mu-list-item-content>
                                <mu-list-item-title>挂号级别
                                </mu-list-item-title>
                            </mu-list-item-content>
                            <mu-list-item-action>
                                {{filteredTitles[registrationForm.title].name}}
                            </mu-list-item-action>
                        </mu-list-item>

                        <mu-list-item :ripple="false">
                            <mu-list-item-content>
                                <mu-list-item-title>应缴金额
                                </mu-list-item-title>
                            </mu-list-item-content>
                            <mu-list-item-action>
                                ￥{{responseInfo.cost.toFixed(2)}}
                            </mu-list-item-action>
                        </mu-list-item>
                    </mu-list>
                </mu-col>
            </mu-row>
        </mu-container>
    </div>
</template>

<script>

    export default {
        name: "FrontDeskRegister",
        inject: ['reload'],
        data() {
            return {
                newForm: {
                    name: "",
                    gender: 0,
                    birthday: null,
                    IDNumber: null,
                    address: ''
                },
                existForm: {
                    medicalRecordID: ''
                },
                controls: {
                    activeStep: 0,
                    newMedicalRecord: true,
                },
                formValidator: {
                    requiredRules: [{
                        validate: val => !!val || val === 0, message: '必须不为空'
                    }],
                    IDNumberRules: [{
                        validate: val => !!val, message: '必须不为空'
                    }, {
                        validate: val => /(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(val), message: '格式不正确，身份证为18位号码'
                    }],
                    medicalRecordIDRules: [{
                        validate: val => val.length === 13, message: '病历号应为13位数字'
                    }]
                },
                /*用于存放用已有的病历号获取到的病历对象*/
                medicalRecordInfo: {
                    id: null,
                    name: "",
                    gender: null,
                    birthday: null,
                    IDNumber: null,
                    address: ''
                },
                registrationFilter: {
                    department: -1,
                    title: -1
                },
                registrationForm: {
                    department: -1,
                    title: -1,
                    doctor: null,
                    cost: 0
                },
                selects: [],
                doctorColumns: [
                    {title: '姓名', width: 126,},
                    {title: '职称', width: 120, align: 'center'},
                    {title: '科室', width: 120, align: 'center'},
                    {title: '每日限额', width: 86, align: 'center'},
                    {title: '挂号费用', width: 120, align: 'center'},
                ],
                responseInfo: {
                    medicalRecordID: null,
                    registrationID: null,
                    cost: 0
                }
            }
        },
        methods: {
            handleNext() {
                switch (this.controls.activeStep) {
                    case 0:
                        this.handleFirstStep();
                        break;
                    case 1:
                        this.controls.activeStep++;
                        break;
                    case 2:
                        this.handleLastStep();
                        break;
                    case 3:
                        this.handleDone();
                        break;
                }

            },
            handlePrev() {
                if (this.controls.activeStep === 2)
                    if (this.controls.newMedicalRecord) {

                        this.controls.activeStep = 0;
                    } else
                        this.controls.activeStep = 1;
                else
                    this.controls.activeStep--;
            },
            switchForm() {
                this.$refs.existForm.clear();
                this.$refs.newForm.clear();
                this.newForm = {
                    name: "",
                    gender: 0,
                    birthday: null,
                    age: null,
                    IDNumber: null,
                    address: ''
                };
                this.existForm = {
                    medicalRecordID: ''
                };
            },
            getAge(birthday) {
                if (birthday == null)
                    return;
                return parseInt((Date.now() - birthday.getTime() + 1000 * 60 * 60 * 24) / (1000 * 60 * 60 * 24 * 365));
            },
            selectDoctor(index, selects) {
                this.selects = [index];
                this.registrationForm = {
                    title: this.filteredDoctors[index].title,
                    department: this.filteredDoctors[index].department,
                    doctor: this.filteredDoctors[index],
                    cost: this.$utils.titleIDToFee(this.filteredDoctors[index].title) + (this.controls.newMedicalRecord ? 1 : 0)
                }
            },
            handleFirstStep() {
                // 验证表单是否通过
                if (this.controls.newMedicalRecord)
                    this.$refs.newForm.validate().then((result) => {
                        if (result)
                            this.controls.activeStep = 2;
                    })
                else {
                    this.$refs.existForm.validate().then((result) => {
                        if (result) {
                            let response = io.post('query-medical-record-by-id', {
                                method: 'query-medical-record-by-id',
                                params: {id: parseInt(this.existForm.medicalRecordID)}
                            });

                            // 若失败
                            if (response.status) {
                                this.$toast.message(response.msg);
                                return;
                            }
                            // 成功则挂载病历对象信息
                            let {id, name, gender, birthday, IDNumber, address} = response.msg;
                            this.medicalRecordInfo = {
                                id,
                                name,
                                gender,
                                birthday: new Date(birthday),
                                IDNumber,
                                address
                            };
                            this.controls.activeStep = 1;
                        }

                    })
                }
            },
            handleLastStep() {
                if (!this.registrationForm.doctor) {
                    this.$toast.info("未选择医生");
                    return;
                }
                let response = this.submit();
                if (!response.status) {
                    this.responseInfo = response.msg;
                    this.$toast.success("挂号成功");
                    this.controls.activeStep = 3;
                } else
                    this.$toast.message(response.msg);
            },
            handleDone() {
                this.reload();
            },
            submit() {
                let requestBody;
                let method = `front-desk-register-${this.controls.newMedicalRecord ? 'new' : 'exist'}`;
                let registration = {
                    department: this.registrationForm.department,
                    title: this.registrationForm.title,
                    doctor: this.registrationForm.doctor.id,
                    cost: this.registrationForm.cost
                };
                // 新建病历的挂号请求
                if (this.controls.newMedicalRecord)
                    requestBody = {
                        method,
                        params: {
                            medicalRecord: {
                                name: this.newForm.name,
                                gender: this.newForm.gender,
                                birthday: this.newForm.birthday.getTime(),
                                IDNumber: this.newForm.IDNumber,
                                address: this.newForm.address
                            },
                            registration
                        }
                    }
                // 已有病历的挂号请求
                else
                    requestBody = {
                        method,
                        params: {
                            medicalRecordID: this.medicalRecordInfo.id,
                            registration
                        }

                    }
                return io.post(method, requestBody);
            }
        },
        computed: {
            age() {
                return this.getAge(this.newForm.birthday);
            },
            departments() {
                return this.$store.state.global.departments
            },
            filteredDepartments() {
                let tmp = {};
                Object.values(this.departments).forEach(i => {
                    if (i.clazz === this.$utils.departmentClazzMap.OUTPATIENT)
                        tmp[i.id] = i;
                });
                return tmp;
            },
            doctors() {
                return this.$store.state.global.doctors;
            },
            filteredDoctors() {
                this.selects = [];
                this.registrationForm = {
                    department: -1,
                    title: -1,
                    doctor: null,
                    cost: 0
                };
                return Object.values(this.doctors).filter(i => (i.department === this.registrationFilter.department || this.registrationFilter.department === -1)
                    && (i.title === this.registrationFilter.title || this.registrationFilter.title === -1));
            },
            titles() {
                return this.$store.state.global.titles;
            },
            filteredTitles() {
                let tmp = {};
                Object.values(this.titles).forEach(i => {
                    if (i.registrationLevel > 0)
                        tmp[i.id] = i;
                });
                return tmp;
            },
            today() {
                let day = new Date();
                day.setHours(0, 0, 0);
                return day;
            },
            nextButtonTitle() {
                switch (this.controls.activeStep) {
                    case 1:
                        return '确认';
                    case 2:
                        return '结算';
                    case 3:
                        return '完成';
                    default:
                        return '下一步';
                }
            }

        }

    }
</script>

<style scoped>
    #front-desk-register {
        padding: 20px;
    }

    .step-bar {
        display: flex;
        align-items: center !important;
    }

    .step-content {
        margin: 20px;
    }

    .switch {
        margin: 20px 10px;
        text-align: center;
    }

    /*让清算项目信息列表文字靠右边*/
    .select-info li:not(:last-child) .mu-item-title, .select-info li:not(:last-child) .mu-item-sub-title {
        text-align: right;
    }

    /*让结果信息列表文字加大*/
    .success-info .mu-item-action {
        font-size: 1.6em;
    }

    .success-info {
        margin-top: 80px;
    }
</style>
