/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.scene.image.Image
 *  javafx.stage.Stage
 */
package com.ti.et.elg.dataimport.ui.wizard;

import com.ti.et.elg.commonUISupport.CommonUISupportResourceBundle;
import com.ti.et.elg.commonUISupport.TransactionType;
import com.ti.et.elg.commonUISupport.commonConstants.CommonConstants;
import com.ti.et.elg.commonUISupport.errorTranslator.ErrorTranslator;
import com.ti.et.elg.commonUISupport.prompt.PromptDialog;
import com.ti.et.elg.connectServer.exports.TIStatus;
import com.ti.et.elg.dataimport.DIException;
import com.ti.et.elg.dataimport.DIFactory;
import com.ti.et.elg.dataimport.DIInvalidFileSizeException;
import com.ti.et.elg.dataimport.converter.IDITIBcdNumberConverter;
import com.ti.et.elg.dataimport.converter.IDITIVarDataConverter;
import com.ti.et.elg.dataimport.model.DITIDataType;
import com.ti.et.elg.dataimport.model.IDIConfig;
import com.ti.et.elg.dataimport.model.IDITIVarData;
import com.ti.et.elg.dataimport.parser.IDIParser;
import com.ti.et.elg.dataimport.ui.IDIController;
import com.ti.et.elg.dataimport.ui.dialog.DIDialog;
import com.ti.et.elg.dataimport.ui.dialog.IDIDialog;
import com.ti.et.elg.dataimport.util.CommonValidationUtils;
import com.ti.et.elg.dataimport.util.StringUtils;
import com.ti.et.utils.logger.TILogger;
import com.ti.et.utils.resourceManager.TIResourceBundle;
import java.io.File;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public final class DIWizardController
implements IDIController {
    private static final String LOG_TAG = DIWizardController.class.getSimpleName();
    private static final int MAX_MB_FILE_SIZE = 2;
    protected static final int LIMIT_LIST_ROWS = 999;
    protected static final int LIMIT_MATRIX_CELLS = 400;
    protected static final int LIMIT_OF_COLUMNS_FOR_LISTS = 6;
    private static final String DEFAULT_VALUE_EMPTY_CELLS = "0";
    private static final int FIRST_RECORD = 0;
    private static final String POSITION = "p";
    private IDIConfig config = null;
    private IDIDialog dialog = null;

    @Override
    public final List<IDITIVarData> importFiles(List<File> list) {
        List<File> list2;
        ArrayList<IDITIVarData> arrayList = new ArrayList<IDITIVarData>();
        if (list != null && !list.isEmpty() && (list2 = this.pullCsvFiles(list)) != null && !list2.isEmpty()) {
            boolean bl = this.validateNumberOfCsvFiles(list);
            boolean bl2 = true;
            File file = this.getFirstCsvFile(list2);
            if (!bl) {
                boolean bl3 = bl2 = this.showWarningMessage(file) == 1;
            }
            if (bl2 && file != null) {
                try {
                    IDIParser iDIParser = DIFactory.getParser();
                    this.validateFileSize(file);
                    List<Map<String, String>> list3 = iDIParser.parse(file);
                    if (list3.size() == 0) {
                        throw new DIException("The file parsed is empty.");
                    }
                    IDIDialog iDIDialog = this.getDialog();
                    iDIDialog.init(file);
                    this.config = iDIDialog.showAndWait(list3);
                    if (!iDIDialog.isDialogCancelled()) {
                        List<List<String>> list4 = this.filterAndPivotData();
                        IDITIBcdNumberConverter iDITIBcdNumberConverter = DIFactory.getTIBcdNumberConverter();
                        List list5 = (List)iDITIBcdNumberConverter.convert(list4);
                        IDITIVarDataConverter iDITIVarDataConverter = DIFactory.getTIVarConverter(this.config);
                        arrayList.addAll((Collection)iDITIVarDataConverter.convert(list5));
                    }
                }
                catch (Exception var7_8) {
                    TILogger.logError(LOG_TAG, var7_8.getMessage());
                    this.showsErrorMessage(file);
                }
            }
        }
        return arrayList;
    }

    private final List<File> pullCsvFiles(List<File> list) {
        ArrayList<File> arrayList = new ArrayList<File>();
        if (list != null && !list.isEmpty()) {
            for (File file : list) {
                if (!CommonValidationUtils.isCsvFile(file.getAbsolutePath())) continue;
                arrayList.add(file);
            }
        }
        return arrayList;
    }

    protected final boolean validateNumberOfCsvFiles(List<File> list) {
        boolean bl = true;
        int n = 0;
        for (File file : list) {
            if (!CommonValidationUtils.isCsvFile(file.getAbsolutePath())) continue;
            ++n;
        }
        if (n > 1) {
            bl = false;
        }
        return bl;
    }

    private final File getFirstCsvFile(List<File> list) {
        File file;
        File file2 = null;
        if (list != null && !list.isEmpty() && (file = list.get(0)) != null) {
            file2 = CommonValidationUtils.getFilePath(file.getAbsolutePath());
        }
        return file2;
    }

    protected final void validateFileSize(File file) throws DIInvalidFileSizeException, DIException {
        if (file.exists()) {
            int n = (int)file.length();
            int n2 = n / 1048576;
            if (n2 > 2) {
                throw new DIInvalidFileSizeException("File exceeds the permitted size");
            }
        } else {
            throw new DIException("The file doesn't exist");
        }
    }

    protected final List<List<String>> filterAndPivotData() throws DIException {
        IDIParser iDIParser = DIFactory.getParser();
        List<Map<String, String>> list = iDIParser.getParsedData();
        LinkedList<List<String>> linkedList = new LinkedList();
        if (this.config == null) {
            throw new DIException("No configuration provided");
        }
        switch (this.config.getType()) {
            case LIST: {
                linkedList = this.filterAndPivotLists(this.config, list);
                break;
            }
            case MATRIX: {
                linkedList = this.filterAndPivotMatrix(this.config, list);
                break;
            }
            default: {
                throw new DIException("Unknown converter type.");
            }
        }
        return linkedList;
    }

    protected void validateList(List<Map<String, String>> list, IDIConfig iDIConfig) throws DIException {
        int n = 1;
        if (iDIConfig.getNumberOfCols() > list.get(0).size()) {
            throw new DIException("Number of the columns in the file isn't the same of the specified");
        }
        for (Map.Entry<String, String> entry : list.get(0).entrySet()) {
            if (!StringUtils.isNotEmpty(entry.getValue())) {
                throw new DIException("Some of the columns in the first position are empty");
            }
            if (n >= iDIConfig.getNumberOfCols()) break;
            ++n;
        }
        if (iDIConfig.getNumberOfCols() > 6) {
            throw new DIException("Invalid number of columns");
        }
    }

    protected void validateMatrix(IDIConfig iDIConfig) throws DIException {
        if (iDIConfig.getNumberOfCols() * iDIConfig.getNumberOfRows() > 400) {
            throw new DIException("The cells number are more than expected");
        }
    }

    protected List<List<String>> filterAndPivotLists(IDIConfig iDIConfig, List<Map<String, String>> list) throws DIException {
        this.validateList(list, iDIConfig);
        ArrayList<List<String>> arrayList = new ArrayList<List<String>>();
        ArrayList<String> arrayList2 = null;
        int n = iDIConfig.getNumberOfCols() < list.get(0).size() ? iDIConfig.getNumberOfCols() : list.get(0).size();
        int n2 = list.size();
        for (int i = 0; i < n; ++i) {
            String string;
            arrayList2 = new ArrayList<String>();
            for (int j = 0; j < n2 && StringUtils.isNotEmpty(string = list.get(j).get("p" + i)); ++j) {
                arrayList2.add(string);
            }
            arrayList.add(arrayList2);
            if (arrayList.get(i).size() <= 999) continue;
            throw new DIException("The number of rows exceeds the allowed for the lists");
        }
        return arrayList;
    }

    protected List<List<String>> filterAndPivotMatrix(IDIConfig iDIConfig, List<Map<String, String>> list) throws DIException {
        this.validateMatrix(iDIConfig);
        ArrayList<List<String>> arrayList = new ArrayList<List<String>>();
        ArrayList<String> arrayList2 = null;
        int n = iDIConfig.getNumberOfCols();
        int n2 = iDIConfig.getNumberOfRows();
        for (int i = 0; i < n2; ++i) {
            arrayList2 = new ArrayList<String>();
            for (int j = 0; j < n; ++j) {
                if (i < list.size()) {
                    String string = list.get(i).get("p" + j);
                    if (!StringUtils.isNotEmpty(string)) {
                        arrayList2.add("0");
                        continue;
                    }
                    arrayList2.add(string);
                    continue;
                }
                arrayList2.add("0");
            }
            arrayList.add(arrayList2);
        }
        return arrayList;
    }

    private final int showWarningMessage(File file) {
        String[] arrstring = new String[]{CommonUISupportResourceBundle.BUNDLE.getString("Cancel"), "butnCancel", CommonUISupportResourceBundle.BUNDLE.getString("Ok"), "butnOk"};
        String string = MessageFormat.format(CommonUISupportResourceBundle.BUNDLE.getString("importDataDialog.multipleFiles"), file.getName());
        Image image = new Image(this.getClass().getResource("/com/ti/et/elg/commonUISupport/icons/dialog_warning.png").toExternalForm());
        return PromptDialog.showMutipleChoiceDialog(null, string, "dlgChoiceFile", arrstring, image);
    }

    private final void showsErrorMessage(File file) {
        TILogger.logError(LOG_TAG, "Couldn't convert csv file " + file.getAbsolutePath() + ", invalid format");
        String[] arrstring = ErrorTranslator.errorCodeToMessage(TransactionType.IMPORTING_DATA_FROM_FILE, new TIStatus(115), null, file.getAbsolutePath());
        PromptDialog.showUserError(arrstring[0], arrstring[1], CommonConstants.MAIN_STAGE);
    }

    @Override
    public IDIDialog getDialog() {
        if (this.dialog != null) {
            return this.dialog;
        }
        return new DIDialog();
    }

    @Override
    public void setDialog(IDIDialog iDIDialog) throws DIException {
        if (iDIDialog == null) {
            throw new DIException("Argument must not be set to null");
        }
        this.dialog = iDIDialog;
    }

}

