<?php
declare(strict_types = 1);
namespace Glagol\CompileClient\Command;

use Glagol\CompileClient\Socket\Client;
use Glagol\CompileClient\Socket\CompileCommand;
use Glagol\CompileClient\Socket\Response;
use Symfony\Component\Console\Command\Command;
use Symfony\Component\Console\Input\InputInterface;
use Symfony\Component\Console\Output\OutputInterface;

class Compile extends Command
{
    const HOST = '127.0.0.1';
    const PORT = 51151;

    /**
     * {@inheritDoc}
     */
    protected function configure()
    {
        $this->setName('compile')
            ->setDescription('Compile Glagol project');
    }

    /**
     * {@inheritDoc}
     */
    protected function execute(InputInterface $input, OutputInterface $output)
    {
        $client = new Client(self::HOST, self::PORT);
        $client->request(new CompileCommand(getcwd()),
            function (Response $response) use ($client, $output): bool {

            if ($response instanceof Response\AbstractMessage) {
                $output->writeln($response->toString());
            }

            if ($response instanceof Response\End) {
                return false;
            }

            return true;
        });
    }
}