<?php
error_reporting(E_ALL);

$autoloader = require __DIR__ . '/../src/composer_autoload.php';

if (!$autoloader()) {
    die(
        'You need to set up the project dependencies using the following commands:' . PHP_EOL .
        'curl -s http://getcomposer.org/installer | php' . PHP_EOL .
        'php composer.phar install' . PHP_EOL
    );
}

use Symfony\Component\Console\Application;

$application = new Application();

$application->addCommands([
    new \Glagol\CompileClient\Command\Compile()
]);

return $application;
