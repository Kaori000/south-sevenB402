package cn.yznu.modules.sys.controller;

import cn.yznu.common.annotation.SysLog;
import cn.yznu.common.utils.MessageCode;
import cn.yznu.common.utils.R;
import cn.yznu.modules.sys.entity.SysFileListEntity;
import cn.yznu.modules.sys.service.SysFileListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Api(tags="文件上传")
@RestController
@RequestMapping("/sys/file")
public class SysFileUploadController extends AbstractController {

    @Autowired
    private SysFileListService sysFileListService;

    @Value("${kaori.filePrePath}")
    private String filePrePath;

    @Value("${kaori.fileUrlPath}")
    private String fileUrlPath;

    @ApiOperation(value="文件上传")
    @PostMapping("/uploadFile")
    public R uploadFile(MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            SimpleDateFormat df = new SimpleDateFormat("/yyyyMM/dd/");
            String fileName = file.getOriginalFilename();
            String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
            String uuidFileName = UUID.randomUUID().toString() + "." + suffix;
            String fileLastPath = file.getContentType().split("/")[0] + df.format(new Date()) + uuidFileName;
            File saveFile = new File(filePrePath + fileLastPath);
            if (!saveFile.getParentFile().exists()) {
                saveFile.getParentFile().mkdirs();
            }
            try {
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(saveFile));
                out.write(file.getBytes());
                out.flush();
                out.close();
                //压缩文件
//                Thumbnails.of(saveFile).size(100, 100).toFile(filePrePath + fileLastPath + "_thum" + "." + suffix);
                //保存文件到数据库
                SysFileListEntity sysFile = new SysFileListEntity();
                sysFile.setName(fileName);
                sysFile.setFileUuidName(uuidFileName);
                sysFile.setFilePerPath(filePrePath);
                sysFile.setFileLastPath(fileLastPath);
                sysFile.setSuffix(suffix);
                sysFile.setFileSize(file.getSize());
                sysFile.setUserId(getUserId());
                sysFile.setContentType(file.getContentType());
                sysFile.setCreateTime(new Date());
                sysFileListService.save(sysFile);
                return R.ok().put("fileId", sysFile.getId()).put("filePath", fileUrlPath + fileLastPath).put("fileName",sysFile.getName()).put("userId",sysFile.getUserId());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return R.error(MessageCode.FILE_OPERTION_URL_IS_EMPTY.getCode(), "上传失败," + e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
                return R.error(MessageCode.FILE_OPERATION_OTHER_IO_EXCIPTION.getCode(), "上传失败," + e.getMessage());
            }
        } else {
            return R.error(MessageCode.FIEL_OPERATION_IS_EMPTY.getCode(), "上传失败，因为文件为空.");
        }
    }
    @ApiOperation(value="文件删除")
    @DeleteMapping("/delFile")
    public R delFile(String fileId){
        sysFileListService.removeById(fileId);
        return R.ok();
    }

    @SysLog("修改")
    @PatchMapping("/update")
    public R update(@RequestBody SysFileListEntity sysFileList){
        sysFileListService.updateById(sysFileList);

        return R.ok();
    }
}
