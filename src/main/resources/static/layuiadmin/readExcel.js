/**
 * CopyRight zh
 * 2018/09/3
 * positionLocation.js
 * version : 1.0.0
 */
'use strict'

import XLSX from 'xlsx'
import * as Utils from '@/utils/index'
/**
 * 引用: import ReadExcel from '@/utils/readExcel'
 * demo: <input type="file" accept=".xlsx, .xls" class="c-hide upload-excel-input" @change="handleFileChange">
 * handleFileChange(e) {
 *  ReadExcel.readerData(e, function(excelData) {
      that.results = excelData.results // 表格数据
      that.header = excelData.header // 表格头
    })
 * }
 */

/**
 * readerData
 * @param e 读取的input file
 * @param callback 读取excel文件解析成功后的回调函数 返回解析出的数据 excelData
 */
const readExcel = {
    excelData: {
        header: null,
        results: null
    },
    readerData(e, callback) {
        // console.log('readerData()-itemFile', e)
        // console.log(6);
        const _thisSelf = this
        const reader = new FileReader()
        const newFile = e.target.files[0]
        reader.readAsArrayBuffer(newFile)
        reader.onload = function() {
            const data = this.result
            const fixedData = _thisSelf.fixData(data)
            const workbook = XLSX.read(btoa(fixedData), { type: 'base64' })
            const firstSheetName = workbook.SheetNames[0]
            const worksheet = workbook.Sheets[firstSheetName]
            const header = _thisSelf.get_header_row(worksheet)
            const results = XLSX.utils.sheet_to_json(worksheet)
            // console.log('转results日期格式之前data', header, results)
            let k1newItem, newItem, cut_newItem
            for (let k1 = 0; k1 < results.length; k1++) {
                k1newItem = results[k1]
                for (const k2 in k1newItem) {
                    newItem = k1newItem[k2]
                    cut_newItem = newItem.split(' ') // 针对时间格式 09-12-2018 20:40:00
                    const r1 = /^\d{1,4}[-\/]\d{1,2}[-\/]\d{1,4}$/ // 针对时间格式 09-12-2018
                    if (r1.test(newItem)) {
                        results[k1][k2] = _thisSelf.changeDataStr(newItem)
                    } else if (r1.test(cut_newItem[0])) {
                        results[k1][k2] = _thisSelf.changeDataStr(newItem)
                    } else {
                        results[k1][k2] = newItem
                    }
                }
            }
            // console.log('时间格式转换后的results', results)
            _thisSelf.generateDate({ header, results }, callback)
        }
    },
    generateDate({ header, results }, callback) {
        this.excelData.header = header
        this.excelData.results = results
        callback(this.excelData)
        // 上传结束
    },
    handleDrop(e) {
        // console.log(2);
        e.stopPropagation()
        e.preventDefault()
        const files = e.dataTransfer.files
        if (files.length !== 1) {
            this.$message.error('Only support uploading one file!')
            return
        }
        const itemFile = files[0] // only use files[0]
        this.readerData(itemFile)
        e.stopPropagation()
        e.preventDefault()
    },
    handleDragover(e) {
        // console.log(3);
        e.stopPropagation()
        e.preventDefault()
        e.dataTransfer.dropEffect = 'copy'
    },
    changeDataStr(str) {
        const dataStr = new Date(str)
        // let newDateStr = dataStr.replace(/(\d{2,4})([/])(\d{2})([/])(\d{2})/, '$1/$5/$3')
        const newDateStr = Utils.parseTime(dataStr, '{y}-{m}-{d} {h}:{i}:{s}')
        // console.log('这是xx/xx/xx', dataStr, '转成xx-xx-xx后', newDateStr)
        return newDateStr
    },
    fixData(data) {
        // console.log(7);
        let o = ''
        let l = 0
        const w = 10240
        for (; l < data.byteLength / w; ++l) o += String.fromCharCode.apply(null, new Uint8Array(data.slice(l * w, l * w + w)))
        o += String.fromCharCode.apply(null, new Uint8Array(data.slice(l * w)))
        return o
    },
    get_header_row(sheet) {
        // console.log(8);
        const headers = []
        const range = XLSX.utils.decode_range(sheet['!ref'])
        let C
        const R = range.s.r /* start in the first row */
        for (C = range.s.c; C <= range.e.c; ++C) { /* walk every column in the range */
            var cell = sheet[XLSX.utils.encode_cell({ c: C, r: R })] /* find the cell in the first row */
            var hdr = 'UNKNOWN ' + C // <-- replace with your desired default
            if (cell && cell.t) hdr = XLSX.utils.format_cell(cell)
            headers.push(hdr)
        }
        return headers
    }
}

export default readExcel