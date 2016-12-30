<?php
error_reporting(E_ALL);

$autoloader = require __DIR__ . '/composer_autoload.php';

if (!$autoloader()) {
    die(
        'You need to set up the project dependencies using the following commands:' . PHP_EOL .
        'curl -s http://getcomposer.org/installer | php' . PHP_EOL .
        'php composer.phar install' . PHP_EOL
    );
}

use Symfony\Component\Console\Application;
use Symfony\Component\Console\Command\Command;
use Symfony\Component\Console\Input\InputInterface;
use Symfony\Component\Console\Output\OutputInterface;

$application = new Application();

$application->add(new class extends Command {

    protected function configure()
    {
        $this->setName('compile')
            ->setDescription('Compile Glagol project');
    }

    protected function execute(InputInterface $input, OutputInterface $output)
    {
        $port = 51151;
        $host = '127.0.0.1';

        $socket = socket_create(AF_INET, SOCK_STREAM, SOL_TCP);
        if ($socket === false) {
            $output->writeln("socket_create() failed: reason: " . socket_strerror(socket_last_error()));
        }

        $result = socket_connect($socket, $host, $port);
        if ($result === false) {
            $output->writeln("socket_connect() failed.\nReason: ($result) " . socket_strerror(socket_last_error($socket)));
        }

        $in = json_encode(['command' => 'compile', 'path' => getcwd()]) . PHP_EOL;
        socket_write($socket, $in, strlen($in));
        while ($out = socket_read($socket, 2048)) {
            $output->write($out);
        }
        socket_close($socket);
    }
});

return $application;
