<?php
import java.lang.System;

class SystemUtil {
	private static $crlf = System::getProperty("line.separator");
	public static function println($msg) {
		echo $msg . self::$crlf;
	}
}