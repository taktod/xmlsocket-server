<?php
import com.ttProject.php.ArgumentManager;
import java.lang.String;

// シングルトンにする必要あり。
class ArgumentUtil {
	private $args;
	public function __construct() {
		$arg_manager = ArgumentManager::getInstance();
		global $_JAVAARG;
		$this->args = $arg_manager->getArgument($_JAVAARG);
	}
	public function getConnection() {
		return $this->getJavaObject(0);
	}
	public function getStringData() {
		return $this->getIoBufferAsString(1);
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