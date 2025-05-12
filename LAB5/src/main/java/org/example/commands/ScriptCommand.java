package org.example.commands;

import java.util.List;

public interface ScriptCommand {
  int scriptExecution(List<String> lines, int startIndex);
}
