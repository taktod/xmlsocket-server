<?php
import com.ttProject.php.ArgumentManager;
import java.lang.String;

class ArgumentUtil {
	private static $instance = null;
	private $args;
	private function __construct() {
		$arg_manager = ArgumentManager::getInstance();
		global $_JAVAARG;
		$this->args = $arg_manager->getArgument($_JAVAARG);
	}
	public static function getInstance() {
		if(self::$instance == null) {
			self::$instance = new ArgumentUtil();
		}
		return self::$instance;
	}
	public function getAdapter() {
		return $this->getJavaObject(0);
	}
	public function getConnection() {
		return $this->getJavaObject(1);
	}
	public function getStringData() {
		$arg_manager = ArgumentManager::getInstance();
		$string = $this->getIoBufferAsString(2);
		$string = $arg_manager->toByteString($string);
		return pack("H*", $string);
	}
	public function getJavaObject($index) {
		if($index >= count($this->args)) {
			return null;
		}
		return $this->args[$index];
	}
	public function getIoBufferAsString($index) {
		if($index >= count($this->args)) {
			return null;
		}
		return trim(new String($this->args[$index]->array()));
	}
}