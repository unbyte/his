<template>
    <div id="admin-query">
        <mu-container>
            <mu-row>
                <mu-col span="3">
                    <h2>病种</h2>
                    <mu-text-field v-model="diseaseFilterText" placeholder="筛选" full-width></mu-text-field>

                    <div class="tree-container">
                        <div class="block">
                            <el-tree
                                    :data="diseases"
                                    node-key="id"
                                    draggable
                                    :props="treeProps"
                                    :filter-node-method="filterNode"
                                    @node-drag-end="handleDrag"
                                    ref="tree">
                                  <span class="tree-node" slot-scope="{ node, data }">
                                    <span>{{ node.label }}</span>
                                    <span>
                                        <mu-icon size="16" value=":i-icon-view"
                                                 @click.stop="() => view(data)"></mu-icon>

                                        <mu-icon size="16" value=":i-icon-edit"
                                                 @click.stop="() => edit(node,data)"></mu-icon>
                                        <mu-icon size="16" value=":i-icon-delete"
                                                 @click.stop="() => remove(node, data)"></mu-icon>
                                        <mu-icon size="16" value=":i-icon-add"
                                                 @click.stop="() => append(data)"></mu-icon>
                                    </span>
                                  </span>
                            </el-tree>
                        </div>
                    </div>
                </mu-col>
                <mu-col>
                    <h2>病人信息{{!currentPatientDisease ? '' : ' - '+currentPatientDisease}}</h2>

                    <!--                    <mu-text-field v-model="patientFilterText" placeholder="筛选" full-width></mu-text-field>-->

                    <mu-data-table stripe :columns="patientAttr" :sort.sync="sort" @sort-change="handleSortChange"
                                   :data="patients" no-data-text="空数据">
                        <template slot-scope="scope">
                            <td>{{new Date(scope.row.registrationTime).toLocaleDateString()}}</td>
                            <td class="is-right">{{$utils.departmentIDToObject(scope.row.departmentID).name}}</td>
                            <td class="is-right">{{scope.row.diseaseName}}</td>
                            <td class="is-right">{{scope.row.patientName}}</td>
                            <td class="is-right">{{$utils.birthToAge(scope.row.patientBirth)}}</td>
                            <td class="is-right">{{scope.row.gender ? '女':'男'}}</td>
                            <td class="is-right">{{scope.row.doctorName}}</td>
                        </template>
                    </mu-data-table>
                </mu-col>
            </mu-row>
        </mu-container>
        <mu-dialog title="编辑" width="400" max-width="80%" :esc-press-close="false" :overlay-close="false"
                   :open.sync="editVisible">
            <mu-form ref="editForm" :model="editForm">
                <mu-form-item label="助记码" prop="code" :rules="editFormValidator.codeRule">
                    <mu-text-field v-model="editForm.code" prop="code"></mu-text-field>
                </mu-form-item>
                <mu-form-item label="病名全称" prop="name" :rules="editFormValidator.nameRule">
                    <mu-text-field v-model.number="editForm.name" prop="name"></mu-text-field>
                </mu-form-item>
            </mu-form>
            <mu-button slot="actions" flat color="primary" @click="saveEdit">保存</mu-button>
            <mu-button slot="actions" flat @click="closeEdit">取消</mu-button>
        </mu-dialog>
        <mu-dialog title="删除确认" width="360" :open.sync="deleteVisible" :overlay-close="false">
            <p>请确认是否删除此病种。</p>
            <p>该病种下属所有病种将一并被删除。</p>
            <mu-button slot="actions" flat @click="confirmDelete">确认</mu-button>
            <mu-button slot="actions" flat color="primary" @click="closeDelete">取消</mu-button>
        </mu-dialog>
    </div>
</template>

<script>
    export default {
        name: "AdminQuery",
        data() {
            return {
                id: 100,
                diseases: [],
                treeProps: {
                    children: 'children',
                    label: 'name'
                },
                diseaseFilterText: "",
                // patientFilterText: "",
                editVisible: false,
                deleteVisible: false,
                editForm: {
                    node: null,
                    code: '',
                    name: ''
                },
                deleteForm: {
                    node: null,
                    data: null
                },
                editFormValidator: {
                    codeRule: [
                        {validate: (val) => !!val, message: '助记码不能为空'},
                        {validate: (val) => /[a-zA-Z1-9]+/.test(val), message: '助记码只能为数字与字母'}
                    ],
                    nameRule: [
                        {validate: (val) => !!val, message: '病名不能为空'}
                    ]
                },
                sort: {
                    name: '',
                    order: 'asc'
                },
                patients: [],
                patientAttr: [
                    {title: '日期', name: 'registrationTime', width: 96, align: 'center', sortable: true},
                    {title: '科室', name: 'department', width: 96, align: 'center', sortable: true},
                    {title: '病种', name: 'diseaseName', width: 96, sortable: true},
                    {title: '姓名', name: 'patientName', width: 96, align: 'center', sortable: true},
                    {title: '年龄', name: 'patientBirth', width: 48, align: 'center', sortable: true},
                    {title: '性别', name: 'patientGender', width: 48, align: 'center', sortable: true},
                    {title: '医师', name: 'doctorName', width: 96, align: 'center', sortable: true},
                ],
                currentPatientDisease: ''
            }
        },
        methods: {
            append(data) {
                let response = io.post('admin-add-disease', {
                    method: 'admin-add-disease',
                    params: {
                        code: '',
                        name: '新建病种',
                        parentID: data.id
                    }
                });
                if (response.status) {
                    this.$toast.message(response.msg);
                    return;
                }

                const newChild = {id: response.msg, name: '新建病种', code: '', children: []};
                if (!data.children) {
                    this.$set(data, 'children', []);
                }
                data.children.push(newChild);

                this.$toast.success("新建成功");
            },

            remove(node, data) {
                this.deleteForm.node = node;
                this.deleteForm.data = data;
                this.deleteVisible = true;
            },

            edit(node, data) {
                this.editForm = {
                    node,
                    code: data.code,
                    name: data.name
                };
                this.editVisible = true;
            },
            view(data) {
                let response = io.post('query-patients-by-disease-id', {
                    method: 'query-patients-by-disease-id',
                    params: {
                        id: data.id,
                        order: '' // fixed :)
                    }
                });
                if (response.status) {
                    this.$toast.message(response.msg);
                    return;
                }
                this.currentPatientDisease = data.name;
                this.patients = response.msg;
            },
            filterNode(value, data) {
                if (!value) return true;
                return data.name.indexOf(value) !== -1 || data.code.indexOf(value) !== -1;
            },
            closeEdit() {
                this.editVisible = false;
                this.editForm = {
                    node: null,
                    code: '',
                    name: ''
                };
            },
            closeDelete() {
                this.deleteVisible = false;
            },
            saveEdit() {
                this.$refs.editForm.validate().then((result) => {
                        if (result) {
                            let response = io.post('admin-edit-disease', {
                                method: 'admin-edit-disease',
                                params: {
                                    id: this.editForm.node.data.id,
                                    name: this.editForm.name,
                                    code: this.editForm.code,
                                }
                            });
                            if (response.status) {
                                this.$toast.message(response.msg);
                                return;
                            }

                            this.editForm.node.data.code = this.editForm.code;
                            this.editForm.node.data.name = this.editForm.name;

                            this.closeEdit();
                            this.$toast.success("编辑成功");
                        }
                    }
                )
            },
            confirmDelete() {
                let response = io.post('admin-remove-disease', {
                    method: 'admin-remove-disease',
                    params: {
                        id: this.deleteForm.data.id,
                    }
                });
                if (response.status) {
                    this.$toast.message(response.msg);
                    return;
                }

                const parent = this.deleteForm.node.parent;
                const children = parent.data.children || parent.data;
                const index = children.findIndex(d => d.id === this.deleteForm.data.id);
                children.splice(index, 1);
                this.deleteVisible = false;

                this.$toast.success("删除成功");
            },
            handleSortChange({name, order}) {
                this.patients = this.patients.sort((a, b) => order === 'asc' ? a[name] - b[name] : b[name] - a[name]);
            },
            // hook一下拖动，每次拖动都和服务器同步树
            handleDrag(draggingNode, dropNode, dropType, ev) {
                // none - 没动作
                // before - 插入到同级
                // after - 插入到同级
                // inside - 插入到之下
                let response = io.post('admin-update-disease-tree-struct', {
                    method: 'admin-update-disease-tree-struct',
                    params: {
                        nodeID: draggingNode.data.id,
                        dropNodeID: dropNode.data.id,
                        type: dropType
                    }
                });
                if (response.status) {
                    this.$toast.message(response.msg);
                    return;
                }
            },
        },
        watch: {
            diseaseFilterText(val) {
                this.$refs.tree.filter(val);
            },
        },
        mounted() {
            this.diseases = this.$store.state.global.diseases;
        }
    }
</script>

<style scoped>
    .container {
        padding-top: 36px;
    }

    .tree-node {
        flex: 1;
        display: flex;
        align-items: center;
        justify-content: space-between;
        font-size: 14px;
        padding-right: 8px;
    }

    i {
        margin: 0 6px;
    }

    h2 {
        font-weight: 700;
        margin-bottom: 12px;
    }

    >>> .mu-table-header, >>> .mu-table-body {
        width: 100% !important;
    }
</style>
