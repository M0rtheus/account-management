package io.inventi.accountmanagement.helpers;

import io.inventi.accountmanagement.model.Statement;

import java.io.InputStream;
import java.util.List;

public interface CSVHelper {

    List<Statement> parseCSVToStatements(InputStream inputStream);
}
