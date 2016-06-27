package com.hiradimir.sforce.ci

import java.io._

object FileUtils {
  def using[A <% { def close(): Unit }](s: A)(
    f: A => Any) {
    try {
      f(s)
    } finally {
      s.close
    }
  }
  def copyFileBinaly(sFile: File, tFile: File): Unit = {
    // ファイルの存在を確認
    if (!sFile.exists()) return ;

    var srcChannel = new FileInputStream(sFile).getChannel();
    var destChannel = new FileOutputStream(tFile).getChannel();
    try {
      srcChannel.transferTo(0, srcChannel.size(), destChannel);
    } finally {
      srcChannel.close();
      destChannel.close();
    }
  }

  def copyDirectry(sDirectry: File, tDirectry: File): Unit = {
    // コピー元がディレクトリでない場合はfalseを返す
    if (!sDirectry.exists() || !sDirectry.isDirectory()) {
      return ;
    }
    // ディレクトリを作成する
    tDirectry.mkdirs();
    // ディレクトリ内のファイルをすべて取得する
    var files = sDirectry.listFiles();

    // ディレクトリ内のファイルに対しコピー処理を行う
    for (f <- files) {
      if (f.isDirectory()) {
        // ディレクトリだった場合は再帰呼び出しを行う
        copyDirectry(
          new File(sDirectry.toString(), f.getName()),
          new File(tDirectry.toString(), f.getName()));
      } else {
        // ファイルだった場合はファイルコピー処理を行う
        copyFileBinaly(
          new File(sDirectry.toString(), f.getName()),
          new File(tDirectry.toString(), f.getName()));
      }
    }
  }
}